package bishe.menu.web.servlet;

import bishe.menu.domain.File;
import bishe.menu.domain.User;
import bishe.menu.service.FileListService;
import bishe.menu.service.IsLoginService;
import bishe.menu.service.UserService;
import bishe.menu.service.impl.FileListServiceImpl;
import bishe.menu.service.impl.IsLoginServiveImpl;
import bishe.menu.service.impl.UserServiceImpl;
import bishe.menu.util.JedisPoolUtils;
import bishe.menu.util.SerializeUtil;
import bishe.menu.util.TimeTransformer;
import bishe.menu.util.UUIDUtils;

import com.aspose.words.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件
 */
@WebServlet("/uploadServlet")
@MultipartConfig //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        IsLoginService isLoginService = new IsLoginServiveImpl();   //实例化IsLoginService对象
        String token = request.getHeader("token");  //获取请求头中的token
        Jedis jedis = JedisPoolUtils.getJedis();    //初始化jedis
        //token判断是否已经登录
        if (isLoginService.islogin(token) != 1){   //未登录或登录状态已过期(即token无效时)
            responseMap.put("status",401);
            responseMap.put("message","请先登录");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        User user = UserServiceImpl.getUser(token, jedis);
        Part part = request.getPart("file");
        System.out.println(part.toString());
        String disposition = part.getHeader("Content-Disposition");
        System.out.println("dis"+disposition);
        //随机的生存一个32的字符串
        String filename = new UUIDUtils().Get_UUID();
        //获取上传的文件名
        InputStream is = part.getInputStream();
        //动态获取服务器的路径
        String serverpath = request.getServletContext().getRealPath("uploadfile");
        java.io.File dir = new java.io.File(serverpath);
        if (!dir.exists()) {dir.mkdir(); }
        FileOutputStream fos = new FileOutputStream(serverpath+"/"+filename);
        byte[] bty = new byte[1024*1024*50];
        int length =0;
        System.out.println("Start Receive");
        while((length=is.read(bty))!=-1){
            new java.io.File(request.getServletContext().getRealPath("step:"+length)).mkdir();
            System.out.print("S");
            fos.write(bty,0,length);
        }
        fos.close();
        is.close();
        FileListService fileListService = new FileListServiceImpl();

        File file = new File();
        file.setUser_id(user.getId());
        file.setFiletype(Integer.valueOf(request.getParameter("filetype")));
        file.setFee(Integer.valueOf(request.getParameter("fee")));
        file.setTitle(disposition.substring(disposition.lastIndexOf("=")+2,disposition.lastIndexOf('"')));
        System.out.println(file.getTitle());
        file.setFilpath(serverpath+"/");

        file.setFilname(filename);
        file.setUseCoin((Integer.valueOf(request.getParameter("fee"))>0)?1:0);
        file.setPopcount(0);
        UserService userService = new UserServiceImpl();    //初始化userService
        Integer coin = user.getCoin();
        coin+=3;
        user.setCoin(coin);
        userService.updateUser(user);
        jedis.set(token.getBytes(), SerializeUtil.serialize(user),"XX".getBytes(),"EX".getBytes(),60*60*24);
        fileListService.add(file);
        if ("0".equals(request.getParameter("filetype"))){
            try {
                Document doc = new Document(serverpath+"/"+filename);
                ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.PNG);
                iso.setResolution(128);
                iso.setPrettyFormat(true);
                iso.setUseAntiAliasing(true);
                for (int i = 0; i < doc.getPageCount(); i++)
                {
                    iso.setPageIndex(i);
                    doc.save(serverpath+"/"+filename + i + ".jpg", iso);
                }
            } catch (Exception e) {
                responseMap.put("error",e.toString());
                e.printStackTrace();
            }
        }
        responseMap.put("filpath",serverpath+"/");
        responseMap.put("filname",filename);
        responseMap.put("status",1);
        responseMap.put("message","success");
        mapper.writeValue(response.getWriter(),responseMap);
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
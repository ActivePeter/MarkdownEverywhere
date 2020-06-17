package bishe.menu.web.servlet;

import bishe.menu.domain.Buylist;
import bishe.menu.domain.File;
import bishe.menu.domain.User;
import bishe.menu.service.BuylistService;
import bishe.menu.service.FileListService;
import bishe.menu.service.IsLoginService;
import bishe.menu.service.UserService;
import bishe.menu.service.impl.BuylistServiceImpl;
import bishe.menu.service.impl.FileListServiceImpl;
import bishe.menu.service.impl.IsLoginServiveImpl;
import bishe.menu.service.impl.UserServiceImpl;
import bishe.menu.util.JedisPoolUtils;
import bishe.menu.util.SerializeUtil;
import bishe.menu.util.TimeTransformer;
import bishe.menu.util.UUIDUtils;
import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件
 */
@WebServlet("/checkBuyServlet")
@MultipartConfig //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class CheckBuyServlet extends HttpServlet {

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
        FileListService fileListService = new FileListServiceImpl();
        File file = fileListService.searchFile(Integer.valueOf(request.getParameter("fileId")));
        if (file.getUseCoin().equals(0)){
            if (file.getFiletype()==1){
                responseMap.put("status",1);
                responseMap.put("pagecount",1);
                mapper.writeValue(response.getWriter(),responseMap);
                jedis.close();
                return;
            }
            try {
                Document document = new Document(file.getFilpath()+file.getFilname());
                responseMap.put("status",1);
                responseMap.put("pagecount",document.getPageCount());
                mapper.writeValue(response.getWriter(),responseMap);
                jedis.close();
                return;
            } catch (Exception e) {
                responseMap.put("status",500);
                mapper.writeValue(response.getWriter(),responseMap);
                jedis.close();
                return;
            }

        }
        BuylistService buylistService = new BuylistServiceImpl();
        User user = UserServiceImpl.getUser(token, jedis);
        System.out.println(Integer.valueOf(request.getParameter("fileId"))+" user:"+user.getId()+" author:"+file.getUser_id()+" "+(user.getId()-file.getUser_id()));
        Buylist buylist = buylistService.searchByUserId(user.getId(), Integer.valueOf(request.getParameter("fileId")));
        if (buylist==null&&user.getId()-(file.getUser_id())!=0){//考虑到作者本人不收费
            Document document = null;
            try {
                document = new Document(file.getFilpath()+file.getFilname());
                responseMap.put("pagecount",document.getPageCount());
            } catch (Exception e) {
                e.printStackTrace();
            }

            responseMap.put("status",402);

            responseMap.put("message","请先购买剧本");
            jedis.close();
            mapper.writeValue(response.getWriter(),responseMap);
            return;
        }
        if (file.getFiletype()==1){
            responseMap.put("status",1);
            responseMap.put("pagecount",1);
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        try {
            System.out.println("hello");
            Document document = new Document(file.getFilpath()+file.getFilname());
            responseMap.put("status",1);
            responseMap.put("message","success");
            responseMap.put("pagecount",document.getPageCount());
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        } catch (Exception e) {
            responseMap.put("status",500);
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
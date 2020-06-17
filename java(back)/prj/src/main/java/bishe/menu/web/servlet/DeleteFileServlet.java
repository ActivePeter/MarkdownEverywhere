package bishe.menu.web.servlet;

import bishe.menu.domain.File;
import bishe.menu.domain.User;
import bishe.menu.service.BuylistService;
import bishe.menu.service.FileListService;
import bishe.menu.service.IsLoginService;
import bishe.menu.service.impl.BuylistServiceImpl;
import bishe.menu.service.impl.FileListServiceImpl;
import bishe.menu.service.impl.IsLoginServiveImpl;
import bishe.menu.service.impl.UserServiceImpl;
import bishe.menu.util.JedisPoolUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户删除自己上传的文件
 * Post请求
 */
@WebServlet("/deleteFileServlet")
public class DeleteFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        //设置编码
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
        BuylistService buylistService = new BuylistServiceImpl();
        FileListService fileListService = new FileListServiceImpl();    //初始化FileListService

        File file = fileListService.searchFile(Integer.valueOf(request.getParameter("FileId")));

        buylistService.delete(file.getId());
        fileListService.delete(file.getId());
        responseMap.put("status",1);
        responseMap.put("message","success");
        mapper.writeValue(response.getWriter(),responseMap);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

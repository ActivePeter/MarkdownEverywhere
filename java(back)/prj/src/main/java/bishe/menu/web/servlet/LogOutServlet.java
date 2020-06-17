package bishe.menu.web.servlet;

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
 * 注销登录
 * Post请求
 */
@WebServlet("/logOutServlet")
public class LogOutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        String token = request.getHeader("token");
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.del(token);
        System.out.println("loggout");
        responseMap.put("status",1);
        responseMap.put("message","success");
        mapper.writeValue(response.getWriter(),responseMap);
        jedis.close();
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

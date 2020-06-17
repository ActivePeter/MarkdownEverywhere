package bishe.menu.web.servlet;

import bishe.menu.domain.DramaInfo;
import bishe.menu.domain.User;
import bishe.menu.service.DramaInfoService;
import bishe.menu.service.IsLoginService;
import bishe.menu.service.impl.DramaInfoServiceImpl;
import bishe.menu.service.impl.IsLoginServiveImpl;
import bishe.menu.util.JedisPoolUtils;
import bishe.menu.util.TimeTransformer;
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
 * 管理员添加话剧信息
 * Post
 */
@WebServlet("/addDramaServlet")
public class AddDramaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        HttpSession session = request.getSession(); //初始化session
        IsLoginService isLoginService = new IsLoginServiveImpl();   //实例化IsLoginService对象
        String token = request.getHeader("token");  //获取请求头中的token
        Jedis jedis = JedisPoolUtils.getJedis();    //初始化jedis
        DramaInfoService dramaInfoService = new DramaInfoServiceImpl();    //初始化DramaInfoService
        //token判断是否已经登录
        if (isLoginService.islogin(token) != 1){   //未登录或登录状态已过期(即token无效时)
            responseMap.put("status",401);
            responseMap.put("message","请先登录");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user.getIsSuperUser()!= 1){ //判断用户是否为管理员
            responseMap.put("status",401);
            responseMap.put("message","该用户不是管理员");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        DramaInfo dramaInfo = new DramaInfo();
        try {
            dramaInfo.setPicpath(request.getParameter("picpath"));
            dramaInfo.setPicname(request.getParameter("picname"));
            dramaInfo.setShowname(request.getParameter("showname"));
            dramaInfo.setStarttime(request.getParameter("starttime"));
            dramaInfo.setEndtime(request.getParameter("endtime"));
            dramaInfo.setMoney(request.getParameter("money"));
            dramaInfo.setLocation(request.getParameter("location"));
            dramaInfo.setAddtime(String.valueOf(new TimeTransformer().getNowTimeStamp()));
            dramaInfoService.add(dramaInfo);
        } catch (Exception e) {
            responseMap.put("status",444);
            responseMap.put("message","fail");
            mapper.writeValue(response.getWriter(),responseMap);
            return;
        }
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

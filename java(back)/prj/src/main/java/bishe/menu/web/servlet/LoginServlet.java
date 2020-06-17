package bishe.menu.web.servlet;

import bishe.menu.domain.User;
import bishe.menu.service.IsLoginService;
import bishe.menu.service.UserService;
import bishe.menu.service.impl.IsLoginServiveImpl;
import bishe.menu.service.impl.UserServiceImpl;
import bishe.menu.util.JedisPoolUtils;
import bishe.menu.util.SerializeUtil;
import bishe.menu.util.UUIDUtils;
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
 * 登录接口
 * Post
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码,判断token是否过期
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        UUIDUtils uuidUtils = new UUIDUtils();
        String uuid = uuidUtils.Get_UUID();//生成一个uuid
        Jedis jedis = JedisPoolUtils.getJedis();//初始化redis连接池
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        IsLoginService isLoginService = new IsLoginServiveImpl();
        String token = request.getHeader("token");
        System.out.println(token);
        //2.token判断是否已经登录
        Integer tokenStatus = isLoginService.islogin(token);
        if (tokenStatus == 1){
            //responseMap = isLoginService.responseMap(responseMap,token,jedis,session);
            //mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        //2.获取数据
        //2.1获取用户填写验证码
        String verifycode = request.getParameter("verifycode");

        if(!verifycode.toLowerCase().equals(CheckCodeServlet.checkCode.toLowerCase())){
            //验证码不正确
            responseMap.put("status",401);
            responseMap.put("message","error");
            mapper.writeValue(response.getWriter(), responseMap);
            return;
        }
        //4.获取登录的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null){
            responseMap.put("status",401);
            responseMap.put("message","用户名或密码不能为空");
            mapper.writeValue(response.getWriter(), responseMap);
            jedis.close();
            return;
        }
        //5.调用Service查询
        UserService loginService = new UserServiceImpl();
        User loginUser = loginService.login(username,password);
        //6.判断是否登录成功
        if(loginUser != null){
            //登录成功
            //将用户存入session
            //session.setAttribute("user",loginUser);
            System.out.println("jedis:"+jedis.set(uuid.getBytes(), SerializeUtil.serialize(loginUser),"NX".getBytes(),"EX".getBytes(),60*60*24));
            responseMap.put("token",uuid);
            responseMap.put("status",1);
            responseMap.put("message","success");
            jedis.close();
            mapper.writeValue(response.getWriter(), responseMap);
        }else{
            //登录失败
            responseMap.put("status",401);
            responseMap.put("message","用户名已存在");
            mapper.writeValue(response.getWriter(), responseMap);
            jedis.close();
        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

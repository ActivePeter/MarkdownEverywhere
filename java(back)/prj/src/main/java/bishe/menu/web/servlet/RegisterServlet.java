package bishe.menu.web.servlet;


import bishe.menu.service.IsRegisterService;
import bishe.menu.service.impl.IsRegisterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static sun.misc.Version.println;

/**
 * 注册接口
 * Post
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null){
            responseMap.put("status",401);
            mapper.writeValue(response.getWriter(), responseMap);
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
        //5.调用IsRegisterService查询
        IsRegisterService isRegisterService = new IsRegisterServiceImpl();
        //6.判断登录名是否已经被使用
        if(isRegisterService.isRegister(username)){
            //用户名已存在
            responseMap.put("status",401);
            mapper.writeValue(response.getWriter(), responseMap);
        }else{
            //注册成功，将用户名加入redis 和 mysql 中
            isRegisterService.addusername(username,password);
            responseMap.put("status",1);
            mapper.writeValue(response.getWriter(), responseMap);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

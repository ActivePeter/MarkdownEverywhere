package bishe.menu.web.servlet;

import bishe.menu.service.DramaInfoService;
import bishe.menu.service.impl.DramaInfoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 展示全部最近话剧信息
 * Get
 */
@WebServlet("/findAllDramaServlet")
public class FindAllDramaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        DramaInfoService dramaInfoService = new DramaInfoServiceImpl();
        responseMap.put("status",1);
        responseMap.put("message","success");
        responseMap.put("list", dramaInfoService.FindAllDrama());
        mapper.writeValue(response.getWriter(),responseMap);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

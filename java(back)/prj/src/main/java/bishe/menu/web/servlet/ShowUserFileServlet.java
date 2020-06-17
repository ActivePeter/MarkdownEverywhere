package bishe.menu.web.servlet;

import bishe.menu.service.FileListService;
import bishe.menu.service.impl.FileListServiceImpl;
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
 * 展示用户上传的全部文件
 * Get请求
 * type为1时展示视频文件
 * type为0时展示文本文件
 */
@WebServlet("/showUserFileServlet")
public class ShowUserFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        if (request.getParameter("type") == null){
            responseMap.put("status",444);
            responseMap.put("message","类型错误");
            mapper.writeValue(response.getWriter(),responseMap);
            return;
        }
        Integer type = Integer.parseInt(request.getParameter("type"));
        FileListService fileListService = new FileListServiceImpl();
        fileListService.FindByFileType(type);
        responseMap.put("status",1);
        responseMap.put("message","success");
        responseMap.put("list", fileListService.FindByFileType(type));
        mapper.writeValue(response.getWriter(),responseMap);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

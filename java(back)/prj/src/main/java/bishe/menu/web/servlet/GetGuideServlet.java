package bishe.menu.web.servlet;

import bishe.menu.domain.User;
import bishe.menu.service.IsLoginService;
import bishe.menu.service.impl.IsLoginServiveImpl;
import bishe.menu.util.JedisPoolUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Get请求获取"如何编写剧本"的内容
 */
@WebServlet("/guideServlet")
public class GetGuideServlet extends HttpServlet {
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//初始化mapper
        Map<String, Object> responseMap = new HashMap<String, Object>();//初始化用于向前端返回json数据的HashMap
        IsLoginService isLoginService = new IsLoginServiveImpl();   //实例化IsLoginService对象
        String token = request.getHeader("token");  //获取请求头中的token
        Jedis jedis = JedisPoolUtils.getJedis();    //初始化jedis
        //token判断是否已经登录
        if (isLoginService.islogin(token) != 1) {   //未登录或登录状态已过期(即token无效时)
            responseMap.put("status", 401);
            responseMap.put("message", "请先登录");
            mapper.writeValue(response.getWriter(), responseMap);
            jedis.close();
            return;
        }
        File file=new File(request.getServletContext().getRealPath("/Guide"));
        if(!file.exists()){
            file.mkdir();
        }
        System.out.println("page:"+request.getParameter("page"));
        String input_path = request.getServletContext().getRealPath("/Guide") + "/guide" + request.getParameter("page") + ".txt";
        file=new File(input_path);
        if(!file.exists()){
            file.createNewFile();
        }
        if(request.getParameter("mode").equals("read")){
            StringBuilder total = new StringBuilder();
            String line;

            try (FileReader reader = new FileReader(input_path);
                 BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
            ) {
                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    total.append(line+System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            responseMap.put("txt", total);
            responseMap.put("status", 1);
            mapper.writeValue(response.getWriter(), responseMap);
            jedis.close();
        }else if(request.getParameter("mode").equals("write")){
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                System.out.println(request.getParameter("content"));
                System.out.println(unicodeToString(request.getParameter("content")));
                fw.write(unicodeToString(request.getParameter("content")));
                fw.flush();
                fw.close();
                responseMap.put("status", 1);
                mapper.writeValue(response.getWriter(), responseMap);
                jedis.close();
            } catch (IOException e) {
                e.printStackTrace();
                responseMap.put("status", 401);
                mapper.writeValue(response.getWriter(), responseMap);
                jedis.close();
            }
        }

        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

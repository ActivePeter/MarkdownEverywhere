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
 * 购买文件
 * Post
 */
@WebServlet("/buyFileServlet")
public class BuyFileServlet extends HttpServlet {
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
        if (isLoginService.islogin(token) != 1){   //未登录或登录状态已过期(即token无效时)
            responseMap.put("status",401);
            responseMap.put("message","请先登录");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }

        FileListService fileListService = new FileListServiceImpl();    //初始化FileListService
        if(request.getParameter("id") == null){
            responseMap.put("status",454);
            responseMap.put("message","fail");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        Integer fileID = Integer.valueOf(request.getParameter("id")); //获取请求体中的id字段
        File file = fileListService.searchFile(fileID);
        if (file == null){          //如果查询到的资源为空
            responseMap.put("status",404);
            responseMap.put("message","请求购买的资源不存在");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        User user = UserServiceImpl.getUser(token, jedis);
        BuylistService buylistService = new BuylistServiceImpl();   //初始化buylistService
        Buylist buylist = buylistService.searchByUserId(user.getId(),fileID); //查询是否已经购买,避免重复购买
        if (buylist != null){   //已经购买过
            responseMap.put("status",3);
            responseMap.put("message","bought");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }

        if (user.getCoin() - file.getFee() < 0){
            responseMap.put("status",454);
            responseMap.put("message","余额不足");
            mapper.writeValue(response.getWriter(),responseMap);
            jedis.close();
            return;
        }
        Integer coin = user.getCoin() - file.getFee();//计算余额
        user.setCoin(coin);//设置余额

        UserService userService = new UserServiceImpl();

        User author=userService.findUserByUsername(userService.userDetail(file.getUser_id()).getName());
        author.setCoin(author.getCoin()+file.getFee());
        userService.updateUser(author);

        buylist=new Buylist();
        //填充buylist对象
        buylist.setUser_id(user.getId());
        buylist.setFilelist_id(fileID);
        buylist.setAddtime(String.valueOf(new TimeTransformer().getNowTimeStamp()));
        //调用buylistService的add方法添加一条购买记录
        buylistService.add(buylist);


        userService.updateUser(user);   //更新user对象
        //更新jedis中缓存的user对象
        jedis.set(token.getBytes(), SerializeUtil.serialize(user),"XX".getBytes(),"EX".getBytes(),60*60*24);
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

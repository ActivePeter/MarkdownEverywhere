package bishe.menu.service.impl;

import bishe.menu.domain.User;
import bishe.menu.service.IsLoginService;
import bishe.menu.util.JedisPoolUtils;
import bishe.menu.util.SerializeUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class IsLoginServiveImpl implements IsLoginService {
    @Override
    public Integer islogin(String token) {
        //1.先从redis中查询数据
        //1.1获取redis客户端连接
        Jedis jedis = JedisPoolUtils.getJedis();
        String loginState = jedis.get(token);
        jedis.close();

        //2判断用户的token是否存在于redis中
        if(loginState == null || loginState.length() == 0){
            //说明redis中没有数据,需要重新登录
            return 0;
        }
        else {
            return 1;   //redis中有数据,不需要重新登录
        }
    }

    @Override
    public Map<String, Object> responseMap(Map<String, Object> responseMap, String token, Jedis jedis,HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        if (loginUser!=null){
            responseMap.put("status",1);
            responseMap.put("message","success");
            responseMap.put("token",token);
            return responseMap;
        }
        byte[] data100= jedis.get((token).getBytes());
        loginUser = (User) SerializeUtil.unserialize(data100);//反序列化Jedis中存储的用户信息
        session.setAttribute("user",loginUser);
        responseMap.put("status",1);
        responseMap.put("message","success");
        responseMap.put("token",token);
        return responseMap;
    }


}

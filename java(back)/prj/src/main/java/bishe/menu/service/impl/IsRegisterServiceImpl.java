package bishe.menu.service.impl;

import bishe.menu.domain.User;
import bishe.menu.service.IsRegisterService;
import bishe.menu.service.UserService;
import bishe.menu.util.JedisPoolUtils;
import bishe.menu.util.TimeTransformer;
import redis.clients.jedis.Jedis;

public class IsRegisterServiceImpl implements IsRegisterService {
    //声明UserService
    private UserService userService = new UserServiceImpl();
    //声明user
    User user;// = new User();

    @Override
    public Boolean isRegister(String username) {
        user = new User();
        //1.先从redis中查询数据
        //1.1获取redis客户端连接
        Jedis jedis = JedisPoolUtils.getJedis();
        String Have_username = jedis.get(username); //前往redis缓存中查找是否有该账号
        if (Have_username != null) {
            return true;
        }
        user = userService.findUserByUsername(username);    //前往MySQL查找是否有该账号
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public void addusername(String username, String password) {
        user = new User();
        TimeTransformer timeTransformer = new TimeTransformer();
        user.setUsername(username);
        user.setPassword(password);
        user.setAddtime(String.valueOf(timeTransformer.getNowTimeStamp()));
        user.setName(username);
        user.setAge(0);
        user.setCoin(0);
        user.setIsSuperUser(0);
        user.setIsUser(1);
        user.setTel("无");
        userService.register(user);
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set(username, "true");
        jedis.close();
    }
}

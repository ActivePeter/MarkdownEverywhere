package bishe.menu.service.impl;

import bishe.menu.dao.UserDao;
import bishe.menu.dao.impl.UserDaoImpl;
import bishe.menu.domain.User;
import bishe.menu.service.UserService;
import bishe.menu.util.SerializeUtil;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username,String password) {
        return userDao.login(username,password);
    }

    @Override
    public void register(User user) {
        userDao.register(user);
    }


    @Override
    public List<Map<String, Object>> userFile(Integer id) {
        return userDao.userFile(id);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public User userDetail(Integer id) {
        return userDao.userDetail(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
    public static User getUser(String token, Jedis jedis) {
        User loginUser;
        byte[] data100= jedis.get((token).getBytes());

        loginUser = (User) SerializeUtil.unserialize(data100);//反序列化Jedis中存储的用户信息
        return loginUser;
    }
}

package bishe.menu.dao;

import bishe.menu.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    User login(String username,String password);

    /**
     * 用户注册
     * @param user
     */
    void register(User user);


    /**
     * 通过用户的id找到用户的所有文件
     * @param id
     * @return
     */
    List<Map<String,Object>> userFile(Integer id);

    /**
     * 删除用户
     * @param id
     */
    void delete(Integer id);

    /**
     * 用户详情页
     * @param id
     * @return
     */
    User userDetail(Integer id);

    /**
     * 更新用户信息(包含签到和打赏消费的积分)
     * @param user
     */
    void updateUser(User user);

    /**
     * 通过用户的登录名查找用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);

}

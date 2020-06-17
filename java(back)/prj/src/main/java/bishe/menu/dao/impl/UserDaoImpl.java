package bishe.menu.dao.impl;

import bishe.menu.dao.UserDao;
import bishe.menu.domain.User;
import bishe.menu.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User login(String username, String password) {
        //用户登录
        User user = null;
        try {
            String sql = "select * from user where username = ? AND password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void register(User user) {
        //添加用户
        try {
            String sql = "insert into user values(null,?,?,?,?,?,?,?,?,?)";
            template.update(sql, user.getName(), user.getAge(), user.getTel(),user.getUsername(),
                    user.getPassword(),user.getCoin(),user.getIsSuperUser(),user.getIsUser(),user.getAddtime());
        } catch (DataAccessException e) {

        }
    }

    @Override
    public List<Map<String, Object>> userFile(Integer id) {
        //通过用户id查询用户拥有的所有文件
        List<Map<String,Object>> list = null;
        try {
            String sql = "SELECT * FROM filelist WHERE user_id = ?";
            list = template.queryForList(sql,id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void delete(Integer id) {
        //删除用户
        try {
            String sql = "delete from user where id = ?";
            template.update(sql, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User userDetail(Integer id) {
        //通过用户id查找用户的详细信息
        User user = null;
        try {
            String sql = "select * from user where id = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        //更新用户信息
        try {
            String sql = "update user set name = ?, age = ?, tel = ?, coin = ? where id = ?";
            template.update(sql, user.getName(),user.getAge(),user.getTel(),user.getCoin(),user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findUserByUsername(String username) {
        System.out.println("user:"+username);
        //通过登录名查找用户
        User user = null;
        try {
            String sql = "select * from user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {
            return user;
        }
        return user;
    }

}

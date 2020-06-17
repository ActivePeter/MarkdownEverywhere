package bishe.menu.dao.impl;

import bishe.menu.dao.BuylistDao;
import bishe.menu.domain.Buylist;
import bishe.menu.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class BuylistDaoImpl implements BuylistDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void add(Buylist buylist) {
        try {
            String sql = "insert into buylist values(null,?,?,?)";
            template.update(sql, buylist.getUser_id(),buylist.getFilelist_id(),buylist.getAddtime());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            String sql = "delete from buylist where filelist_id = ?";
            //String sql = "delete from buylist where id = ?";
            template.update(sql, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Buylist buylist) {
        //更新购买记录
        try {
            String sql = "update buylist set user_id = ?, filelist_id = ?, addtime = ? where id = ?";
            template.update(sql, buylist.getUser_id(),buylist.getFilelist_id(),buylist.getAddtime(),buylist.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Buylist searchByUserId(Integer user_id,Integer filelist_id) {
        //通过用户id和文件id查找用户是否已购买
        Buylist buylist = null;
        try {
            String sql = "select * from buylist where user_id = ? AND filelist_id = ?";
            buylist = template.queryForObject(sql, new BeanPropertyRowMapper<>(Buylist.class), user_id,filelist_id);
        } catch (DataAccessException e) {
            return null;
            //e.printStackTrace();

        }
        return buylist;
    }

    @Override
    public List<Map<String, Object>> userBuyList(Integer id) {
        //通过用户id查询所有的购买记录
        List<Map<String,Object>> list = null;
        try {
            String sql = "SELECT * FROM buylist WHERE user_id = ?";
            list = template.queryForList(sql,id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}

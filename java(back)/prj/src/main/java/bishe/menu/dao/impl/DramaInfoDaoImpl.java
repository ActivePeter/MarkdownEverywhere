package bishe.menu.dao.impl;

import bishe.menu.dao.DramaInfoDao;
import bishe.menu.domain.DramaInfo;
import bishe.menu.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class DramaInfoDaoImpl implements DramaInfoDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void add(DramaInfo dramaInfo) {
        //添加最近话剧信息
        try {
            String sql = "insert into dramainfo values(null,?,?,?,?,?,?,?,?)";
            template.update(sql, dramaInfo.getPicpath(), dramaInfo.getPicname(), dramaInfo.getShowname(), dramaInfo.getStarttime()
                    , dramaInfo.getEndtime(), dramaInfo.getMoney(), dramaInfo.getLocation(), dramaInfo.getAddtime());
        } catch (DataAccessException e) {

        }
    }

    @Override
    public void delete(Integer id) {
        //删除用户
        try {
            String sql = "delete from dramainfo where id = ?";
            template.update(sql, id);
        } catch (DataAccessException e) {

        }
    }

    @Override
    public List<Map<String, Object>> FindAllDrama() {
        //找到所有最近话剧信息
        List<Map<String, Object>> list = null;
        try {
            String sql = "SELECT * FROM dramainfo";
            list = template.queryForList(sql);
        } catch (DataAccessException e) {
        }
        return list;
    }

    @Override
    public DramaInfo update(DramaInfo dramaInfo) {
        //修改最近话剧信息
        try {
            String sql = "update dramainfo set picpath = ?, picname = ?, showname = ?, starttime = ?, endtime=?, " +
                    "money = ?, location = ? where id = ?";
            template.update(sql, dramaInfo.getPicpath(), dramaInfo.getPicname(), dramaInfo.getShowname(), dramaInfo.getStarttime()
                    , dramaInfo.getEndtime(), dramaInfo.getMoney(), dramaInfo.getLocation(), dramaInfo.getAddtime(),dramaInfo.getId());
        } catch (DataAccessException e) {
        }
        return dramaInfo;
    }

    @Override
    public DramaInfo ModifyDramaDetail(Integer id) {
        //找到的一条话剧信息
        DramaInfo dramaInfo = null;
        try {
            String sql = "SELECT * FROM dramainfo where id = ?";
            dramaInfo = template.queryForObject(sql, new BeanPropertyRowMapper<>(DramaInfo.class), id);
        } catch (DataAccessException e) { }
        return dramaInfo;
    }
}

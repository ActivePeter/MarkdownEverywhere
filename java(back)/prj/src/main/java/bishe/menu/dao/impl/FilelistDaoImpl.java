package bishe.menu.dao.impl;

import bishe.menu.dao.FilelistDao;
import bishe.menu.domain.File;
import bishe.menu.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class FilelistDaoImpl implements FilelistDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void add(File file) {
        //添加文件
        try {
            String sql = "insert into filelist values(null,?,?,?,?,?,?,?,?)";
            template.update(sql, file.getUser_id(),file.getFilname(),file.getFilpath(),file.getUseCoin(),file.getFee(),
                    file.getPopcount(),file.getTitle(),file.getFiletype());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Integer id) {
        //删除文件
        try {
            String sql = "delete from filelist where id = ?";
            template.update(sql, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File searchFile(Integer id) {
        //通过文件id查找文件的所有信息
        File file = null;
        try {
            String sql = "select * from filelist where id = ?";
            file = template.queryForObject(sql, new BeanPropertyRowMapper<>(File.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void updateFile(File file) {
        try {
            String sql = "update filelist set useCoin = ?, fee = ? where id = ?";
            template.update(sql, file.getUseCoin(),file.getFee(),file.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> FindByFileType(Integer id) {
        //通过文件类型查询所有文件
        List<Map<String,Object>> list = null;
        try {
            String sql = "SELECT * FROM filelist WHERE filetype = ?";
            list = template.queryForList(sql,id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public void PopFile(File file) {
        try {
            String sql = "update filelist set popcount = ? where id = ?";
            template.update(sql, file.getPopcount(),file.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package bishe.menu.dao;

import bishe.menu.domain.File;

import java.util.List;
import java.util.Map;

public interface FilelistDao {

    /**
     * 上传文件
     *
     * @param
     */
    void add(File file);

    /**
     * 删除文件
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 通过文件id查找文件的详细信息
     *
     * @param id
     * @return
     */
    File searchFile(Integer id);

    /**
     * 更新文件信息
     *
     * @param file
     */
    void updateFile(File file);

    /**
     * 根据文件类型查找
     * @param id
     * @return
     */
    List<Map<String, Object>> FindByFileType(Integer id);
    public void PopFile(File file);
}

package bishe.menu.service.impl;

import bishe.menu.dao.FilelistDao;
import bishe.menu.dao.impl.FilelistDaoImpl;
import bishe.menu.domain.File;
import bishe.menu.service.FileListService;

import java.util.List;
import java.util.Map;

public class FileListServiceImpl implements FileListService {
    FilelistDao filelistDao = new FilelistDaoImpl();
    @Override
    public void add(File file) {
        filelistDao.add(file);
    }

    @Override
    public void delete(Integer id) {
        filelistDao.delete(id);
    }

    @Override
    public File searchFile(Integer id) {
        return filelistDao.searchFile(id);
    }

    @Override
    public void updateFile(File file) {
        filelistDao.updateFile(file);
    }

    @Override
    public List<Map<String, Object>> FindByFileType(Integer id) {
        return filelistDao.FindByFileType(id);
    }

    @Override
    public void PopFile(File file) {
        filelistDao.PopFile(file);
    }
}

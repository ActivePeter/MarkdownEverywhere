package bishe.menu.service.impl;

import bishe.menu.dao.DramaInfoDao;
import bishe.menu.dao.impl.DramaInfoDaoImpl;
import bishe.menu.domain.DramaInfo;
import bishe.menu.service.DramaInfoService;

import java.util.List;
import java.util.Map;

public class DramaInfoServiceImpl implements DramaInfoService {

    DramaInfoDao dramainfoDao = new DramaInfoDaoImpl();

    @Override
    public void add(DramaInfo dramaInfo) {
        dramainfoDao.add(dramaInfo);
    }

    @Override
    public void delete(Integer id) {
        dramainfoDao.delete(id);
    }

    @Override
    public List<Map<String, Object>> FindAllDrama() {
        return dramainfoDao.FindAllDrama();
    }

    @Override
    public DramaInfo ModifyDramaDetail(Integer id) {
        return dramainfoDao.ModifyDramaDetail(id);
    }

    @Override
    public DramaInfo update(DramaInfo dramaInfo) {
        return dramainfoDao.update(dramaInfo);
    }
}

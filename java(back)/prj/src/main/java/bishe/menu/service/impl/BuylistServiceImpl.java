package bishe.menu.service.impl;

import bishe.menu.dao.BuylistDao;
import bishe.menu.dao.impl.BuylistDaoImpl;
import bishe.menu.domain.Buylist;
import bishe.menu.service.BuylistService;

import java.util.List;
import java.util.Map;

public class BuylistServiceImpl implements BuylistService {
    BuylistDao buylistDao = new BuylistDaoImpl();
    @Override
    public void add(Buylist buylist) {
        buylistDao.add(buylist);
    }

    @Override
    public void delete(Integer id) {
        buylistDao.delete(id);
    }

    @Override
    public void update(Buylist buylist) {
        buylistDao.update(buylist);
    }

    @Override
    public Buylist searchByUserId(Integer user_id, Integer filelist_id) {
        return buylistDao.searchByUserId(user_id, filelist_id);
    }

    @Override
    public List<Map<String, Object>> userBuyList(Integer id) {
        return buylistDao.userBuyList(id);
    }
}

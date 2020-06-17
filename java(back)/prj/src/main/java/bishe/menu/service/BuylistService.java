package bishe.menu.service;

import bishe.menu.domain.Buylist;

import java.util.List;
import java.util.Map;

public interface BuylistService {
    /**
     * 增加购买记录
     * @param buylist
     */
    void add(Buylist buylist);

    /**
     * 删除购买记录
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改购买记录
     * @param buylist
     */
    void update(Buylist buylist);

    /**
     * 通过用户id和文件id查找用户是否已购买
     * @param user_id
     * @param filelist_id
     * @return
     */
    Buylist searchByUserId(Integer user_id,Integer filelist_id);

    /**
     * 通过用户id查询所有的购买记录
     * @param id
     * @return
     */
    List<Map<String, Object>> userBuyList(Integer id);
}

package bishe.menu.service;

import bishe.menu.domain.DramaInfo;

import java.util.List;
import java.util.Map;

public interface DramaInfoService {
    /**
     * 增加最近话剧信息
     * @param dramaInfo
     */
    void add(DramaInfo dramaInfo);

    /**
     * 删除某条最近话剧信息
     * @param id
     */
    void delete(Integer id);

    /**
     * 获得最近所有的话剧信息
     * @return
     */
    List<Map<String,Object>> FindAllDrama();

    /**
     * 更新一条话剧信息
     * @param dramaInfo
     * @return
     */
    DramaInfo update(DramaInfo dramaInfo);

    /**
     * 获得一条话剧信息的详细信息
     * @param id
     * @return
     */
    DramaInfo ModifyDramaDetail(Integer id);

}

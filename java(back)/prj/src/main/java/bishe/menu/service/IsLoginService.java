package bishe.menu.service;

import bishe.menu.domain.User;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface IsLoginService {

    /**
     * 检查登录是否过期或尚未登录
     * @param token
     * @return
     */
    Integer islogin(String token);

    /**
     * 若已登录,将用户信息从redis中读入session.
     * @param responseMap
     * @param session
     * @param jedis
     * @return
     */
    Map<String, Object> responseMap(Map<String, Object> responseMap, String token, Jedis jedis,HttpSession session);

    /**
     * 读取Session中或Jedis中的用户信息
     * @param token
     * @param jedis
     * @param session
     * @return
     */

}

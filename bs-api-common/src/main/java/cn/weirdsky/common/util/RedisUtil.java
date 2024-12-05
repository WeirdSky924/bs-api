package cn.weirdsky.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisUtil {

    /**
     * 删除指定session
     * @param sessionId
     * @return
     */
    boolean deleteSession(String sessionId);

}

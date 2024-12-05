package cn.weirdsky.utils.util;

public interface RedisUtil {

    /**
     * 删除指定session
     * @param sessionId
     * @return
     */
    boolean deleteSession(String sessionId);

}

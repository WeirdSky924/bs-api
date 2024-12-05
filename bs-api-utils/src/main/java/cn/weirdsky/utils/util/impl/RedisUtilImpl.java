package cn.weirdsky.utils.util.impl;

import cn.weirdsky.utils.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisUtilImpl implements RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean deleteSession(String sessionId) {
        Set<String> keys = stringRedisTemplate.keys(sessionId);
        Long delete = stringRedisTemplate.delete(keys);
        return delete==1;
    }
}

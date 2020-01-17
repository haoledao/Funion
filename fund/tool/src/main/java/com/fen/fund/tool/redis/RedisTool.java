package com.fen.fund.tool.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author Fei
 * @date 2020-01-12
 */
@Slf4j
@Component
public class RedisTool {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    protected void expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("REDIS TOOL - > 指定缓存失效时间异常", e);
        }
    }

    /**
     * 获取缓存的长度
     * @param key 键
     */
    protected Long getSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("REDIS TOOL - > 获取缓存长度异常", e);
            return 0L;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个或多个值
     */
    @SuppressWarnings("unchecked")
    protected void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

}

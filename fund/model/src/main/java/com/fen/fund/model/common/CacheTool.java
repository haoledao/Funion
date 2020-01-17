package com.fen.fund.model.common;

import com.fen.fund.model.fund.FundType;
import com.fen.fund.tool.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具
 * @author Fei
 * @date 2020-01-12
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class CacheTool extends RedisTool {

    /** 设置List缓存 */
    public void setList(String key, Object value, String time) {
        try {
            del(key);
            redisTemplate.opsForList().rightPush(key, value);
            if (StringUtils.isNotBlank(time)) {
                expire(key, Long.valueOf(time));
            }
        } catch (Exception e) {
            log.error("CACHE TOOL - > 设置List缓存异常", e);
        }
    }

    /** 普通缓存获取 */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /** 普通缓存放入 */
    public void set(String key, Object value) {
        try {
            del(key);
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("REDIS TOOL - > 普通缓存放入异常", e);
        }
    }

    /** 普通缓存放入并设置时间 */
    public boolean set(String key, Object value, String time) {
        try {
            del(key);
            if (StringUtils.isNotBlank(time)) {
                redisTemplate.opsForValue().set(key, value, Long.valueOf(time), TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("REDIS TOOL - > 普通缓存放入并设置时间异常", e);
            return false;
        }
    }

    /** 获取基金类型缓存 */
    public List<FundType> getFundTypes(String key) {
        try {
            List<Object> list = redisTemplate.opsForList().range(key, 0, getSize(key));
            if (list == null || list.size() == 0) {
                return null;
            }
            Object object = list.get(0);
            return new ArrayList<>(((List<FundType>) object));
        } catch (Exception e) {
            log.error("CACHE TOOL - > 获取GameResult缓存异常", e);
            return null;
        }
    }

}

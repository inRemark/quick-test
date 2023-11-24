package com.example.redis.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils
{
    @Resource
    private RedisTemplate<String, Object>   redisTemplate;
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    public final static long                DEFAULT_EXPIRE = 60 * 60 * 24;
    public final static long                NOT_EXPIRE     = -1;


    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public void set(String key, Object value)
    {
        set(key, value, DEFAULT_EXPIRE);
    }

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public String get(String key)
    {
        return valueOperations.get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        String value = valueOperations.get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    public void delete(String key)
    {
        redisTemplate.delete(key);
    }

    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String)
        {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }
    private <T> T fromJson(String json, Class<T> clazz)
    {
        return JSON.parseObject(json, clazz);
    }

}

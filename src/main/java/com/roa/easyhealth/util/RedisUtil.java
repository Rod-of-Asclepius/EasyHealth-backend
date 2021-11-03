package com.roa.easyhealth.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public Object get(Object o) {
        if (o == null) {
            return null;
        }

        return redisTemplate.opsForValue().get(o);
    }
    public String getString(Object o) {
        return (String) get(o);
    }

    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(Object key, Object value, long l) {
        redisTemplate.opsForValue().set(key, value, l, TimeUnit.SECONDS);
    }

    public void set(Object key, Object value, long l, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, l, timeUnit);
    }

    public Boolean del(Object key) {
        if (key == null) return Boolean.FALSE;
        return redisTemplate.delete(key);
    }

    public long getExpire(Object key, TimeUnit timeUnit){
        final Long expire = redisTemplate.getExpire(key, timeUnit);
        if(expire==null){
            return 0;
        }else{
            return expire;
        }
    }

    public Boolean addExpire(Object key, long addTime, TimeUnit timeUnit){
        final Long expire = redisTemplate.getExpire(key, timeUnit);
        if(expire==null){
            return redisTemplate.expire(key, addTime, timeUnit);
        }else{
            long ee = timeUnit.convert(expire,timeUnit)+addTime;
            return redisTemplate.expire(key, ee, timeUnit);
        }
    }

    public Boolean reduceExpire(Object key, long reduceTime, TimeUnit timeUnit){
        final Long expire = redisTemplate.getExpire(key, timeUnit);
        if(expire==null){
            return redisTemplate.expire(key, 0, timeUnit);
        }else{
            long ee = timeUnit.convert(expire,timeUnit)-reduceTime;
            if(ee<0){
                ee = 0 ;
            }
            return redisTemplate.expire(key, ee, timeUnit);
        }
    }

}

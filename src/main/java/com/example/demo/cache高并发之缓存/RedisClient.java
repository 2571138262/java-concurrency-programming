package com.example.demo.cache高并发之缓存;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * http://redis.cn/
 * redis客户端封装
 */
@Component
public class RedisClient {
    
    @Resource(name = "redisPool")
    private JedisPool jedisPool;
    
    // get
    public void set(String key, String value) throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
    
    public String get(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
    
}

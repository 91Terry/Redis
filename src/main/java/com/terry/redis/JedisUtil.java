package com.terry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtil {
    private static JedisPool jedisPool = null;

    public static Jedis getJedisFromPool(){
        if (jedisPool == null){
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(100);
            jedisPoolConfig.setMaxIdle(5);
            jedisPoolConfig.setMinIdle(10);
            jedisPoolConfig.setBlockWhenExhausted(true);
            jedisPoolConfig.setMaxWaitMillis(2000);
            jedisPoolConfig.setTestOnBorrow(true);
            jedisPoolConfig.setTestWhileIdle(true);

            JedisPool jedisPool = new JedisPool(jedisPoolConfig, "hadoop102", 6379);
            return jedisPool.getResource();
        }else {
            return jedisPool.getResource();
        }
    }

}

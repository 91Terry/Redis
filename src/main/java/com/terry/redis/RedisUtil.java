package com.terry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import javax.xml.soap.Text;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;

public class RedisUtil {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedisFromPool();

        Set<String> keyset = jedis.keys("*");
        for (String key : keyset) {
            System.out.println(key);
        }

        Set<Tuple> top3 = jedis.zrevrangeWithScores("article:topn", 0, 2);
        for (Tuple tuple : top3) {
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }

        System.out.println(jedis.ping());
        jedis.close();
    }

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

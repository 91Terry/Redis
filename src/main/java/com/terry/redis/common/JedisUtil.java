package com.terry.redis.common;

import redis.clients.jedis.*;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class JedisUtil {
    //设置连接池
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

    //设置哨兵池
    private static JedisSentinelPool jedisSentinelPool = null;

    public static Jedis getJedisFromSentinel(){
        if (jedisSentinelPool == null){
            Set<String> sentinelSet = new HashSet<String>();
            sentinelSet.add("hadoop102:26379");

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10); //最大可用连接数
            jedisPoolConfig.setMaxIdle(5); //最大闲置连接数
            jedisPoolConfig.setMinIdle(5); //最小闲置连接数
            jedisPoolConfig.setBlockWhenExhausted(true); //连接耗尽是否等待
            jedisPoolConfig.setMaxWaitMillis(2000); //等待时间
            jedisPoolConfig.setTestOnBorrow(true); //取连接的时候进行一下测试 ping pong

            JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(
                    "mymaster", sentinelSet, jedisPoolConfig);

            return jedisSentinelPool.getResource();
        }else {
            return jedisSentinelPool.getResource();
        }
    }

    private static JedisCluster jedisCluster = null;

    public static JedisCluster getJedisCluster(){
        if (jedisCluster == null){
            Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
            hostAndPortSet.add(new HostAndPort("hadoop102",6390));
            hostAndPortSet.add(new HostAndPort("hadoop102",6391));

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10); //最大可用连接数
            jedisPoolConfig.setMaxIdle(5); //最大闲置连接数
            jedisPoolConfig.setMinIdle(5); //最小闲置连接数
            jedisPoolConfig.setBlockWhenExhausted(true); //连接耗尽是否等待
            jedisPoolConfig.setMaxWaitMillis(2000); //等待时间
            jedisPoolConfig.setTestOnBorrow(true); //取连接的时候进行一下测试 ping pong

            JedisCluster jedisCluster = new JedisCluster(hostAndPortSet, jedisPoolConfig);
            return jedisCluster;
        }else {
            return jedisCluster;
        }
    }

}

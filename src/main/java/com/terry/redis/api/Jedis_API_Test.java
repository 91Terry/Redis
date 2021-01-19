package com.terry.redis.api;

import com.terry.redis.common.JedisUtil;
import redis.clients.jedis.Jedis;

public class Jedis_API_Test {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        String pong = jedis.ping();

        System.out.println("连接成功："+pong);

        jedis.close();
    }
}

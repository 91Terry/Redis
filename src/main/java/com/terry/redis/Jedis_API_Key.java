package com.terry.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class Jedis_API_Key {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        jedis.set("k10","100");
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());
        for (String key : keys) {
            System.out.println(key);
        }

        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.get("k1"));

        //查看还有多少秒过期
        System.out.println(jedis.ttl("k1"));
    }
}

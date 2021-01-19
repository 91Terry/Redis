package com.terry.redis.test;

import com.terry.redis.common.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class Sentinel {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromSentinel();
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        jedis.close();
    }
}

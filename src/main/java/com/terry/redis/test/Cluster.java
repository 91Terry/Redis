package com.terry.redis.test;

import com.terry.redis.common.JedisUtil;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

public class Cluster {
    public static void main(String[] args) {
        JedisCluster jedisCluster = JedisUtil.getJedisCluster();

        jedisCluster.set("k100","v100");
        String k = jedisCluster.get("k100");
        System.out.println(k);

        jedisCluster.close();
    }
}

package com.terry.redis.api;

import com.terry.redis.common.JedisUtil;
import redis.clients.jedis.Jedis;

public class Jedis_API_String {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        //同时设置多个kv
        jedis.mset("str1","v1","str2","v2","str3","v3");
        System.out.println(jedis.mget("str1", "str2", "str3"));

        //追加值
        jedis.append("str1","v5");
        System.out.println(jedis.get("str1"));

    }
}

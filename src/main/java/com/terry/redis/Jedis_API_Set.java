package com.terry.redis;

import redis.clients.jedis.Jedis;


public class Jedis_API_Set {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        //Set集合无序，不可重复，可以用来数据去重
        //添加多个元素到集合key里
        jedis.sadd("s1","v1","v2","v3","v3","v5");
        jedis.sadd("s2","v2","v3","v4");

        //返回集合的元素个数
        System.out.println(jedis.scard("s1"));
        System.out.println(jedis.scard("s2"));

        //返回集合的所有值
        System.out.println(jedis.smembers("s1"));
        System.out.println(jedis.smembers("s2"));

        //返回两个集合的交集、并集、差集元素
        System.out.println(jedis.sinter("s1", "s2"));
        System.out.println(jedis.sunion("s1", "s2"));
        System.out.println(jedis.sdiff("s1", "s2"));

        //删除集合元素
        jedis.srem("s1","v5");
    }
}

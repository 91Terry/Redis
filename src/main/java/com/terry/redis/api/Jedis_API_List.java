package com.terry.redis.api;

import com.terry.redis.common.JedisUtil;
import redis.clients.jedis.Jedis;


public class Jedis_API_List {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        //List有序可重复
        //从左边插入多个值
        jedis.lpush("l1","v1","v2","v3","v4");

        //获取列表长度
        System.out.println(jedis.llen("l1"));

        //按照索引下标获取元素
        System.out.println(jedis.lrange("l1", 0, -1));

    }
}

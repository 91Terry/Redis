package com.terry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.lang.annotation.ElementType;
import java.util.Set;

public class Jedis_API_Zset {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        /*
        zset和set类似，没有重复元素，不同的是zset
        每个元素关联一个score，元素是有序的
         */
        //加入多个member元素以及score的值
        jedis.zadd("z1",100,"v1");
        jedis.zadd("z1",200,"v2");
        jedis.zadd("z1",150,"v3");

        //返回有序集合
        Set<Tuple> tuples = jedis.zrangeWithScores("z1", 0, -1);
        for (Tuple tuple : tuples) {
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }
    }
}

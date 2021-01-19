package com.terry.redis.api;

import com.terry.redis.common.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;


public class Jedis_API_Hash {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        //hash适合用于存储对象
        //给<key>集合中的  <field>键赋值<value>
        jedis.hset("h1","user","zhangsan");

        //获取集合对应属性的值
        System.out.println(jedis.hget("h1", "user"));

        //集合中多个属性赋值
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user","terry");
        map.put("telphone","1383899999");
        map.put("address","beijing");
        map.put("email","terry@qq.com");
        jedis.hmset("h2",map);
        List<String> result = jedis.hmget("h2", "user", "telphone");
        for (String element : result) {
            System.out.println(element);
        }
    }
}

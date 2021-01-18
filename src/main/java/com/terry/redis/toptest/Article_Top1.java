package com.terry.redis.toptest;

import com.terry.redis.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class Article_Top1 {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedisFromPool();

        /*
        利用zset实现一个文章访问量的排行榜？
         */
        //随机生成文章点击量
        for (int i = 0; i < Math.random()*100; i++) {
            jedis.zincrby("article:topn",1,"article_1");
        }
        for (int i = 0; i < Math.random()*100; i++) {
            jedis.zincrby("article:topn",1,"article_2");
        }
        for (int i = 0; i < Math.random()*100; i++) {
            jedis.zincrby("article:topn",1,"article_3");
        }
        for (int i = 0; i < Math.random()*100; i++) {
            jedis.zincrby("article:topn",1,"article_4");
        }

        Set<Tuple> tuples = jedis.zrevrangeWithScores("article:topn", 0, -1);
        for (Tuple tuple : tuples) {
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }
    }
}

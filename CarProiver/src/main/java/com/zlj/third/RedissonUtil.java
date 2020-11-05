package com.zlj.third;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * projectName: Cloud_Like
 * author: 张留佳
 * time: 2020/11/2 20:36
 * description: 基于Redisson实现Redis数据的操作
 */

public class RedissonUtil {

    private static RedissonClient client;
    static {
        Config config = new Config();
        config.setThreads(1000);
        config.useSingleServer().setAddress("redis://39.105.189.141:6380").setPassword("qfjava");  // 单机

        client = Redisson.create(config);
    }

    // 新增
    public static void setStr(String key,String val){
        client.getBucket(key).set(val);
    }

    public static void setStr(String key,String val,long seconds){
        client.getBucket(key).set(val,seconds, TimeUnit.SECONDS);
    }

    public static void setSet(String key,String val){
        client.getSet(key).add(val);
    }

    public static void setSet(String key, Set<Object> vals){
        client.getSet(key).addAll(vals);
    }

    public static void setSet(String key,double score,String val){
        client.getScoredSortedSet(key).add(score,val);
    }

    public static void setSet(String key, Map<Object,Double> vals){
        client.getScoredSortedSet(key).addAll(vals);
    }

    // 查询
    public static String getStr(String key){
        return client.getBucket(key).get().toString();
    }
    public static Set<Object> getSet(String key){
        return client.getSet(key).readAll();
    }
    public static Collection<Object> getzSet(String key){
        return client.getScoredSortedSet(key).readAll();
    }
    public static boolean checkSet(String key,Object val){
        return client.getSet(key).contains(val);
    }
    public static boolean checkzSet(String key,Object val){
        return client.getScoredSortedSet(key).contains(val);
    }
    public static boolean checkKey(String key){
        return client.getKeys().countExists(key) > 0;
    }

    // 设置有效期
    public static void setTime(String key,long time,TimeUnit timeUnit){
        client.getKeys().expire(key, time, timeUnit);
    }

    // 移除元素
    public static boolean delZet(String key,Object val){
        return client.getScoredSortedSet(key).remove(val);
    }
    // 删除key
    public static void delKey(String... key){
        client.getKeys().delete(key);
    }

    public static void delHash(String key,Object val){
        client.getMap(key).remove(val);
    }

    // 分布式锁  Redis = (Setnx + Java中的LOCK)
    // 加锁
    public static void lock(String key){
        client.getLock(key).lock();
    }
    // 加锁设置有效期  超出有效期自动释放
    public static void lock(String key,long seconds){
        client.getLock(key).lock(seconds,TimeUnit.SECONDS);
    }
    // 释放锁
    public static void unlock(String key){
        client.getLock(key).unlock();
    }

    public static Iterable<String> getKeys(String key){
        return client.getKeys().getKeysByPattern(key);
    }






}

package com.qianxuefeng.redis;

import redis.clients.jedis.Jedis;

import static com.sun.scenario.Settings.set;

/**
 * @author Shawn Qian
 * @since 2018年05月03日
 */
public class RedisConnectTest {

    private static Jedis jedis = new Jedis("120.26.62.83", 6379);

    public static void main(String[] args) {
        if (trylock("abc")) System.out.println("获得锁1"); else System.out.println("获取失败1");
        if (trylock("abc")) System.out.println("获得锁2"); else System.out.println("获取失败2");
        if (trylock("abc")) System.out.println("获得锁3"); else System.out.println("获取失败3");

        if (unlock("abc")) System.out.println("释放锁1"); else System.out.println("释放失败1");
        if (unlock("abc")) System.out.println("释放锁2"); else System.out.println("释放失败2");
        if (unlock("abc")) System.out.println("释放锁3"); else System.out.println("释放失败3");
    }


    public static boolean trylock(String key) {
        String resultCode = jedis.set(key, "This is a Lock.");
        return "OK".equals(resultCode);
    }
    public static boolean unlock(String key) {
        Long resultNum = jedis.del(key);
        return resultNum == 1L;
    }
}
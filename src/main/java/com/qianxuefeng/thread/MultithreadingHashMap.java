package com.qianxuefeng.thread;

import java.util.HashMap;
import java.util.UUID;

/**
 * 多线程环境下，
 * 使用Hashmap进行put操作会引起死循环，
 * 导致CPU利用率接近100%，
 * 所以在并发情况下不能使用HashMap
 *
 * @author Shawn Qian
 * @since 2018年03月14日
 */
public class MultithreadingHashMap {
    public static void main(String[] args) {
        try {
            final HashMap<String, String> map = new HashMap<String, String>(2);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                map.put(UUID.randomUUID().toString(), "");
                            }
                        }, "ftf" + i).start();
                    }

                }

            }, "ftf");

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
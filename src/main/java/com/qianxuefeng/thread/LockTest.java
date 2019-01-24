package com.qianxuefeng.thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qianxuefeng
 * @since 2017年01月20日
 */
public class LockTest {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(lock.tryLock()) {
                    try {
                        for (int i = 0; i < 1; i++) {
                            int ran = new Random().nextInt() % 10;
                            System.out.println(Thread.currentThread().getName() + "：" + ran);
//                            try {
//                                Thread.sleep(1100 - 100 * ran);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };


        for (int i = 0; i < 100; i++){
            new Thread(runnable, "线程" + (i + 1)).start();
        }

    }

}
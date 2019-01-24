package com.qianxuefeng.base;

import java.util.Date;

/**
 * @author qianxuefeng
 * @since 2017年01月17日
 */
public class ObjectTest {

    public void finalize() throws Throwable{
        super.finalize();
        System.out.println("finalize方法被调用");
    }
    public static void main(String[] args){
        new ObjectTest();
        System.gc();
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        run();
    }

    private static Object lock = new Object();

    public static void run(){
        long times = new Date().getTime();

        MyThread thread1 = new MyThread("线程一", times);
        MyThread thread2 = new MyThread("线程二", times);
        thread1.start();
        thread2.start();

        try {
            System.out.println("主线程准备就绪；");
            Thread.sleep(2 * 1000);
            System.out.println("主线程开始；");
            synchronized (lock) {
                System.out.println("主线程获得同步锁：" + (new Date().getTime() - times)/1000 + "秒");

                System.out.println("主线程唤醒某个等待同步锁的线程；");
//                lock.notify();
//                lock.notify();
//                lock.notifyAll();

                System.out.println("主线程等待5秒；");
                Thread.sleep(5 * 1000);
                System.out.println("主线程等待结束：" + (new Date().getTime() - times)/1000 + "秒");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread{
        long times = -1;

        public MyThread(String name, long times){
            super(name);
            this.times = times;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println(Thread.currentThread() + "获得同步锁：" + (new Date().getTime() - times)/1000 + "秒");
                    lock.wait(10 * 1000);
                    System.out.println(Thread.currentThread() + "结束：" + (new Date().getTime() - times)/1000 + "秒");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
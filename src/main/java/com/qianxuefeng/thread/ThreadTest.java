package com.qianxuefeng.thread;

/**
 * @author qianxuefeng
 * @since 2017年01月20日
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
//        new MyThread("售票口1").start();
//        new MyThread("售票口2").start();
//        new MyThread("售票口3").start();
//        new MyThread("售票口4").start();

        MyRunnable runnable = new MyRunnable();
        Thread t5 = new Thread(runnable, "售票口5");
        t5.start();
//        Thread t6 = new Thread(runnable, "售票口6");
//        t6.start();
//        Thread t7 = new Thread(runnable, "售票口7");
//        t7.start();
//        Thread t8 = new Thread(runnable, "售票口8");
//        t8.start();

        Thread.sleep(500);
        System.out.println(t5.isInterrupted());
        t5.stop();
        System.out.println(t5.isInterrupted());
    }

    static class MyThread extends Thread {
        private int ticket = 10;

        public MyThread(String name) {
            super(name);
        }

        public void run() {
            while (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "：售出第" + (ticket--) + "张票");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static class MyRunnable implements Runnable {

        private int ticket = 100;

        @Override
        public void run() {
            while (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "：售出第" + (ticket--) + "张票");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
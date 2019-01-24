package com.qianxuefeng.thread;

/**
 * @author qianxuefeng
 * @since 2017年01月22日
 */
public class ThreadLocalTest {
    public static void main(String[] args){
        MyRunnable runnable = new MyRunnable();
        new Thread(runnable, "线程1").start();
        new Thread(runnable, "线程2").start();
    }

    public static class MyRunnable implements Runnable {

        private ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
            @Override
            protected String initialValue() {
                return "初始化值";
            }
        };

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            threadLocal.set(name + "的threadLocal");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：" + threadLocal.get());
        }
    }
}
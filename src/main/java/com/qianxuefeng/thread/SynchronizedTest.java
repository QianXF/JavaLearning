package com.qianxuefeng.thread;

/**
 * @author qianxuefeng
 * @since 2017年01月19日
 */
public class SynchronizedTest {

    /** 每个一秒打印一个递增的数字 **/
    private static void print() {
        for (int i = 0; i < 3; i++){
            System.out.println(Thread.currentThread().getName() + "：" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /** 静态方法锁1 **/
    private static synchronized void staticMethodLock1(){
        print();
    }

    /** 静态方法锁2 **/
    private static synchronized void staticMethodLock2(){
        print();
    }

    /** 非静态方法锁1 **/
    private synchronized void methodLock1(){
        print();
    }

    /** 非静态方法锁2 **/
    private synchronized void methodLock2(){
        print();
    }

    /** 对象锁1 **/
    public void objectLock1(){
        synchronized(this) {
            print();
        }
    }

    /** 对象锁1 **/
    public void objectLock2(){
        synchronized(this) {
            print();
        }
    }

    /** 类锁1 **/
    public void classLock1(){
        synchronized(this.getClass()) {
            print();
        }
    }

    /** 类锁1 **/
    public void classLock2(){
        synchronized(this.getClass()) {
            print();
        }
    }

    public static void main(String[] args){
        final SynchronizedTest lock = new SynchronizedTest();

        // 静态方法锁：不同方法，用一个类锁
        // 结果：产生竞争
        new Thread("线程1（静态方法锁1）"){
            public void run(){
                staticMethodLock1();
            }
        }.start();

        new Thread("线程2（静态方法锁2）"){
            public void run(){
                staticMethodLock2();
            }
        }.start();

        // 非静态方法锁：不同方法，同一个对象锁
        // 结果：产生竞争
        new Thread("线程3（非静态方法锁1）"){
            public void run(){
                lock.methodLock1();
            }
        }.start();

        new Thread("线程4（非静态方法锁2）"){
            public void run(){
                lock.methodLock2();
            }
        }.start();

        // 对象锁：同一个方法，同一个类的不同对象锁
        // 结果：不竞争
        new Thread("线程5（对象锁1）"){
            public void run(){
                new SynchronizedTest().objectLock1();
            }
        }.start();

        new Thread("线程6（对象锁2）"){
            public void run(){
                new SynchronizedTest().objectLock1();
            }
        }.start();

        // 对象锁：不同方法，同一个对象锁
        // 结果：产生竞争
        new Thread("线程7（对象锁1）"){
            public void run(){
                lock.objectLock1();
            }
        }.start();

        new Thread("线程8（对象锁2）"){
            public void run(){
                lock.objectLock2();
            }
        }.start();

        // 类锁：两个对象，不同方法，同一个类锁
        // 结果：产生竞争
        new Thread("线程9（类锁1）"){
            public void run(){
                new SynchronizedTest().classLock1();
            }
        }.start();

        new Thread("线程10（类锁2）"){
            public void run(){
                new SynchronizedTest().classLock2();
            }
        }.start();

    }
}
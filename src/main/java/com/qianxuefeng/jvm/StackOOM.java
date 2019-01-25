package com.qianxuefeng.jvm;

/**
 * -Xms1024m -Xmx1024m -Xss2M
 * 通过-Xms1024m -Xmx1024m控制堆内存大小，从而间接控制栈内存大小
 *
 * (MaxProcessMemory - JVMMemory - ReservedOsMemory) / (ThreadStackSize) = Number of threads
 *
 * MaxProcessMemory：进程最大内存，windows 32位 是2G
 * JVMMemory：JVM内存，eclipse默认启动的程序内存是64M
 * ReservedOsMemory：操作系统保留内存，一般是130M左右，不精确
 * ThreadStackSize：单个线程的栈大小，Java5 之前是256K，之后是1M
 *
 * 创建过多线程导致内存溢出
 */
public class StackOOM {
    private static int count = 0;

    public void stackLeakByThread(){
        while(count < 10000){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60 * 60 * 24);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            System.out.println("创建线程" + count++);
        }
    }

    /**
     * 运行参数：
     * -Xms1024m -Xmx1024m -Xss2M
     *
     * 尝试结论：
     * 以上参数，并没有影响线程的创建数量，
     * Mac OS限制了用户线程最大创建数量2500，
     * Linux 限制比较大：
     *          Ubuntu 10.04 Linux Kernel 2.6 32bit : 32080
     *          CentOS 7u2 64bit : 10000
     * @param args
     */
    public static void main(String[] args) {
        StackOOM stackOOM = new StackOOM();
        stackOOM.stackLeakByThread();
    }
}
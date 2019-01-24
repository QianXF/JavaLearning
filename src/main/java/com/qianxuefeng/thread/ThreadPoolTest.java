package com.qianxuefeng.thread;

import java.util.Date;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * @author qianxuefeng
 * @since 2017年01月22日
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        createThreadPool();// 自己创建线程池
//        factoryThreadPool();// 工厂方法创建线程池
    }


    private static void factoryThreadPool(){
        Executors.newSingleThreadExecutor();//单个后台线程

        Executors.newFixedThreadPool(10);//固定大小线程池

        Executors.newCachedThreadPool();//无界线程池，可以进行自动线程回收

        Executors.newScheduledThreadPool(10);//定时线程池
    }

//    public static ExecutorService newSingleThreadExecutor() {
//        return new Executors.FinalizableDelegatedExecutorService
//                (new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
//    }
//    public static ExecutorService newFixedThreadPool(int nThreads) {
//        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//    }
//    public static ExecutorService newCachedThreadPool() {
//        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
//    }


    private static Integer count = 0;
    private static void createThreadPool(){
        int corePoolSize = 2;//线程池的基本大小
        int maximumPoolSize = 4;//线程池最大大小
        int keepAliveTime = 60;//线程活动保持时间
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;//线程活动保持时间的单位
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(10);//任务队列，基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序
        ThreadFactory threadFactory = new ThreadFactory(){
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };//用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字
        ThreadPoolExecutor.DiscardPolicy handler = new ThreadPoolExecutor.DiscardPolicy();//饱和策略，只用调用者所在线程来运行任务（通常会是主线程）
        ThreadPoolExecutor threadPool = new MyThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, taskQueue, threadFactory, handler);

        final long seconds = new Date().getTime();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                int temp;
                synchronized (count){
                    temp = count++;
                }

                System.out.println(Thread.currentThread().getName() + ":任务" + temp + " 第" + ((new Date().getTime() - seconds)/1000) + "秒开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":任务" + temp + " 第" + ((new Date().getTime() - seconds)/1000) + "结束");
            }
        };

        Future future = null;
        for(int i = 0; i < 16 ; i++) {
            if(i == 15){
                future = threadPool.submit(runnable);
                continue;
            }
            threadPool.execute(runnable);
        }

        try {
            assert future != null;
            Object res = future.get();
        } catch (InterruptedException | ExecutionException e) {
            // 处理中断异常
        } finally {
            // 关闭线程池
            threadPool.shutdown();
        }
    }

}

class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("任务准备：taskCount:" + getTaskCount() + ";completedTaskCount:" + getCompletedTaskCount() + ";largestPoolSize:" + getLargestPoolSize() + ";poolSize:" + getPoolSize() + ";activeCount" + getActiveCount());
    }

    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("任务结束：taskCount:" + getTaskCount() + ";completedTaskCount:" + getCompletedTaskCount() + ";largestPoolSize:" + getLargestPoolSize() + ";poolSize:" + getPoolSize() + ";activeCount" + getActiveCount());
    }

    protected void terminated() {
        System.out.println("线程池结束：taskCount:" + getTaskCount() + ";completedTaskCount:" + getCompletedTaskCount() + ";largestPoolSize:" + getLargestPoolSize() + ";poolSize:" + getPoolSize() + ";activeCount" + getActiveCount());
    }
}

package com.qianxuefeng.socket;

import com.qianxuefeng.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class TCPSocketConnect implements Runnable {
    public boolean isConnect = false;// 是否连接服务器
    private boolean isWrite = false;// 是否发送数据
    private Vector<byte[]> datas = new Vector<byte[]>();// 待发送数据队列
    private Object lock = new Object();// 连接锁对象
    private TCPSocketFactory mSocket;// socket连接
    private WriteRunnable writeRunnable;// 发送数据线程
    private HeartbeatRunnable heartbeatRunnable;// 发送数据线程
    private Thread writeThread;
    private Thread heartbeatThread;
    private String ip = null;
    private int port = -1;

    private static int index = 0;

    private String guiNo;

    public TCPSocketConnect(TCPSocketCallback callback) {
        mSocket = new TCPSocketFactory(callback);// 创建socket连接
        writeRunnable = new WriteRunnable();// 创建发送线程
        heartbeatRunnable=new HeartbeatRunnable();//创建心跳线程
    }

    @Override
    public void run() {
        if (ip == null || port == -1) {
            return;
        }
        isConnect = true;
        while (isConnect){
            synchronized (lock){
                try{
                    System.out.println(">TCP开始连接服务器<");
                    mSocket.connect(ip, port);// 连接服务器

                    //这里成功连接服务器之后，需要发送一个设备联机请求
                    //{"code":"0","guiNo":"TA103","msg":"","type":"link"}
                    //guiNo 带入终端号,命令必须以\r\n作为结束
                    String linkcmd=String.format("{\"code\":\"0\",\"guiNo\":\"%s\",\"msg\":\"%s\",\"type\":\"link\"}\r\n",guiNo, DateTimeUtil.formatYMDHMS_HR(new Date()));
                    mSocket.write(linkcmd.getBytes());

                }catch (Exception e){
                    try{
                        System.out.println(">TCP连接服务器失败, 6秒后重新连接<");
                        resetConnect();// 断开连接
                        lock.wait(6000);
                        continue;
                    } catch (InterruptedException e1){
                        continue;
                    }
                }
            }
            System.out.println(">TCP连接服务器成功<");
            isWrite = true;// 设置可发送数据

            try {
                if (writeThread != null
                        && !writeThread.getState().equals(Thread.State.TERMINATED)) {
                    writeThread.stop();
                }

                if (heartbeatThread != null
                        && !heartbeatThread.getState().equals(Thread.State.TERMINATED)) {
                    heartbeatThread.stop();
                }
            }catch (Exception e){

            }

            writeThread = new Thread(writeRunnable, "推送线程-1");
            writeThread.start();
            System.out.println(">TCP连接启动发送线程<");
            heartbeatThread = new Thread(heartbeatRunnable, "心跳线程-1");
            heartbeatThread.start();
            System.out.println(">TCP连接启动心跳<");
            try{
                mSocket.read();// 获取数据
            }catch (Exception e){
                System.out.println(">TCP连接异常<");
            }finally{
                System.out.println(">TCP连接中断<");
                resetConnect();// 断开连接
            }
        }
        System.out.println(">=TCP结束连接线程=<");
    }

    public void disconnect(){
        synchronized (lock) {
            lock.notify();
            resetConnect();
        }
    }

    public void resetConnect(){
        System.out.println(">TCP重置连接<");
//        isConnect = false;
        writeRunnable.stop();// 发送停止信息
        mSocket.disconnect();
    }

    public void write(byte[] buffer){
        writeRunnable.write(buffer);
    }

    public void setAddress(String host, int port){
        this.ip = host;
        this.port = port;
    }

    public void setGuiNo(String guiNo) {
        this.guiNo = guiNo;
    }

    private boolean writes(byte[] buffer){
        try{
            mSocket.write(buffer);
            sleep(1);
            return true;
        }catch (Exception e){
            resetConnect();
            return false;
        }
    }

    private class HeartbeatRunnable implements Runnable{
        private Object wlock = new Object();// 发送线程锁对象
        @Override
        public void run(){
            System.out.println(">TCP心跳线程开启<");
            while (isConnect)
            {
                System.out.println(Thread.currentThread().getName() + "在运行");

                if (datas.size()<=0) {
                    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    String linkcmd = String.format("{\"code\":\"0\",\"guiNo\":\"%s\",\"msg\":\"%s\",\"type\":\"heartbeat\"}\r\n", guiNo,formatter.format(curDate));
                    writes(linkcmd.getBytes());
                }
                try {
                    sleep(10000);
                } catch (Exception e) {
                }
            }


            System.out.println(">TCP心跳线程结束<");
        }


    }

    private class WriteRunnable implements Runnable{
        private Object wlock = new Object();// 发送线程锁对象
        private String threadName = UUID.randomUUID().toString().replaceAll("-", "");

        @Override
        public void run(){
            System.out.println(threadName + "在发送");
            System.out.println(">TCP发送线程开启<");

            while (isWrite){
                synchronized (wlock){
                    if (datas.size() <= 0){
                        try{
                            wlock.wait();// 等待发送数据
                        }catch (InterruptedException e){
                            continue;
                        }
                    }
                    while (datas.size() > 0){
                        byte[] buffer = datas.remove(0);// 获取一条发送数据
                        if (isWrite){
                            writes(buffer);// 发送数据
                        }else{
                            wlock.notify();
                        }
                    }
                }
            }



            System.out.println(">TCP发送线程结束<");
        }

        public void write(byte[] buffer){
            synchronized (wlock){
                datas.add(buffer);// 将发送数据添加到发送队列
                wlock.notify();// 取消等待
            }
        }

        public void stop(){
            synchronized (wlock){
                isWrite = false;
                wlock.notify();
            }
        }
    }
}

package com.qianxuefeng.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * -Xms20M 堆内存最小值
 * -Xmx20M 堆内存最大值
 * -XX:+PrintGCDetails 打印GC信息
 * -XX:+HeapDumpOnOutOfMemoryError 堆内存溢出时dump堆快照信息
 *
 * 堆内存溢出
 */
public class HeapOOM {
    /**
     * 运行参数：
     * -Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/qianxuefeng/git/JavaLearning/log/
     * @param args
     */
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<byte[]>();
        int i = 0;
        boolean flag = true;
        while (flag){
            try {
                i++;
                list.add(new byte[1024 * 1024]);//每次增加一个1M大小的数组对象
            }catch (Throwable e){
                e.printStackTrace();
                flag = false;
                System.out.println("占用堆内存：" + i + "M");//记录运行的次数
            }
        }
    }
}
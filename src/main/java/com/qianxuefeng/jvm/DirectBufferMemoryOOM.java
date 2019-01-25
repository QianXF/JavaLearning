package com.qianxuefeng.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * -XX:MaxDirectMemorySize=10M
 * 直接内存大小
 */
public class DirectBufferMemoryOOM {
    private static int count = 0;

    /**
     * 运行参数：
     * -Xmx20M -XX:MaxDirectMemorySize=10M
     * -XX:MaxDirectMemorySize设置直接内存大小，如果不设置，则和堆最大值一样
     * @param args
     */
    public static void main(String[] args) throws Exception {
        List<ByteBuffer> list = new ArrayList<>();
        while(count < 100000) {
            // JVM堆缓冲区：ByteBuffer.allocate(size)
            // 本地缓冲区：ByteBuffer.allocateDirect(size)
            ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024);//1MB
            list.add(bb);
            System.out.println(count++);
        }
    }
}
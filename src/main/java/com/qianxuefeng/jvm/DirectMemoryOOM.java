package com.qianxuefeng.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * -XX:MaxDirectMemorySize=10M
 * 直接内存大小
 */
public class DirectMemoryOOM {
    private static int count = 0;

    /**
     * 运行参数：
     * -Xmx20M -XX:MaxDirectMemorySize=10M
     * 不起作用
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // 直接内存溢出
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        while (count < 1000000){
            unsafe.allocateMemory(1024 * 1024 * 1024);// 不知道如何控制
            System.out.println(count++);
        }
    }
}
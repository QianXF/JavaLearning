package com.qianxuefeng.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串常量池溢出，Java 6及以下 PermGen space, Java 7及以上 Heap space
 */
public class StringConstantPoolOOM {

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        long l = 0;
        while (l < Long.MAX_VALUE){
            list.add(String.valueOf(l++).intern());
            System.out.println("第" + l);
        }
    }
}
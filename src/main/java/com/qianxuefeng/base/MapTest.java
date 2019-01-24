package com.qianxuefeng.base;

import java.util.HashMap;

/**
 * @author qianxuefeng
 * @since 2017年03月20日
 */
public class MapTest {

    public static void main(String[] args){
//        HashMap<Integer, Integer> map = new HashMap<>(4);
//        for(int i = 0; i < 5; i++){
//            map.put(i, -i);
//        }
//        map.get(1);
//        System.out.println(tableSizeFor(4));
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
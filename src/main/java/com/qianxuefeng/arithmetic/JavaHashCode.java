package com.qianxuefeng.arithmetic;

/**
 * @author Shawn Qian
 * @since 2018年02月24日
 */
public class JavaHashCode {

    // HashMap中实现的hash算法(再hash算法)
    static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private static int hashCode(String str){
        int hashCode = 0;
        for (char ch : str.toCharArray()){
            hashCode = 31 * hashCode + (int)ch;
        }
        return hashCode;
    }

    public static void main(String[] args) {
        String str = "必达我家四";
        System.out.println("MyHashCode: " + hashCode(str));
        System.out.println("JavaHashCode: " + str.hashCode());
        System.out.println("HashMap 中 再次hash: " + hash(hashCode(str)));
    }
}
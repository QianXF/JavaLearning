package com.qianxuefeng.base;

/**
 * @author qianxuefeng
 * @since 2017年01月16日
 */
public class EqualsTest {
    public static void main(String[] args){
        Integer a = new Integer(127);
        Integer b = new Integer(127);
        System.out.println(a == b);
        System.out.println(a.equals(b));

//        System.out.println();

        Integer c = Integer.valueOf(127);
        Integer d = 127;
        System.out.println(c == d);
        System.out.println(c.equals(d));

//        System.out.println();

        Integer e = Integer.valueOf(128);
        Integer f = 128;
        System.out.println(e == f);
        System.out.println(e.equals(f));

        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";

        String s4 = "ab";
        System.out.println("s3==s4? "+ (s3==s4));

        String s5 = "a"+"b";// 编译器优化为 s5="ab"
        System.out.println("s3==s5? "+ (s3==s5));

        String s6 = s1+s2;// 编译器优化为 new StringBuffer("aa").append("bb").toString()
        System.out.println("s3==s6? "+ (s3==s6));

        String s7 = new String("ab");// 根据缓冲池的定义在new的时候实际会新分配地址空间
        System.out.println("s3==s7? "+ (s3==s7));

        final String s8 = "a" ;
        final String s9 = "b" ;
        String s10 = s8 + s9;
        System.out.println("s3==s10? "+ (s3==s10));

    }
}
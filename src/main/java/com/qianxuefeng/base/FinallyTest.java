package com.qianxuefeng.base;

/**
 * @author qianxuefeng
 * @since 2017年01月21日
 */
public class FinallyTest {
    public static void main(String[] args){
        System.out.println("result:" + test());
    }

    private static int test(){
        int a = 0;
        try {
            a++;
            System.out.println("try:" + a);
            return a;
        }catch (Exception e){
            a++;
            System.out.println("catch:" + a);
        }finally {
            a++;
            System.out.println("finally:" + a);
            return a;
        }
//        a++;
//        System.out.println("return:" + a);
//        return a;
    }
}
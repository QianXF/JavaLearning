package com.qianxuefeng.base;

/**
 * @author Shawn Qian
 * @since 2018年10月10日
 */
public class ExtendTest {
    public static void main(String[] args) {
        ExtendSon son = new ExtendSon();
        System.out.println(son.getPrivateKey());
        System.out.println(son.getProtectedKey());
        System.out.println(son.getPublicKey());
    }
}
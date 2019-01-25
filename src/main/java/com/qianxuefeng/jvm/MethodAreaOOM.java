package com.qianxuefeng.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Java 7 及以下
 * -XX:PermSize=8M
 *     永久代最小值
 * -XX:MaxPermSize=8M
 *     永久代最大值
 *
 * Java 8 及以下
 * -XX:MetaspaceSize=8M
 *     元空间最小值
 * -XX:MaxMetaspaceSize=8M
 *     元空间最大值（默认-1无限大）
 *
 * 方法区溢出
 */
public class MethodAreaOOM {

    /**
     * PermGen space
     *     -XX:PermSize=4M -XX:MaxPermSize=4M
     *
     * Meta space
     *     -XX:MetaspaceSize=6M -XX:MaxMetaspaceSize=6M
     */
    public static void main(String[] args) {
        int count = 0;
        while(count < 100000) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, args1, proxy) -> proxy.invokeSuper(o, args1));
            enhancer.create();
            System.out.println(count++);
        }
    }

    static class OOMObject{}
}
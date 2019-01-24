package com.qianxuefeng.base;

/**
 * @author qianxuefeng
 * @since 2017年01月21日
 */
class OverrideTest {
    public static void main(String[] args) {
        Parent apple = new Son();
        apple.say();
    }

}

class Parent {

    void say() {
        System.out.println("---------父类----------");
        printName();
        staticMethod();
        finalMethod();
    }

    void printName() {
        System.out.println("我是父类");
    }

    static void staticMethod() {
        System.out.println("父类static方法");
    }

    final void finalMethod() {
        System.out.println("父类final方法");
    }
}

class Son extends Parent {

    void say() {
        super.say();
        System.out.println("---------子类----------");
        printName();
        staticMethod();
        finalMethod();
    }

    void printName() {
        System.out.println("我是子类");
    }

    static void staticMethod() {
        System.out.println("子类static方法");
    }

}

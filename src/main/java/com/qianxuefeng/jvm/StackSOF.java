package com.qianxuefeng.jvm;

/**
 * -Xss1M
 * 默认设置每个线程的堆栈大小1M
 *
 * 栈溢出
 */
public class StackSOF {
    private static int index = 1;
 
    public void call(){
        index++;
        call();
    }

    /**
     * 运行参数：
     * -Xss256K
     * 可尝试变更Xss，由此改变每个线程堆栈的深度
     * @param args
     */
    public static void main(String[] args) {
        StackSOF mock = new StackSOF();
        try {
            mock.call();
        }catch (Throwable e){
            System.out.println("栈深度：" + index);
            e.printStackTrace();
        }
    }
}
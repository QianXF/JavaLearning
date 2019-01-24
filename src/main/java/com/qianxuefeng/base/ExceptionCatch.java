package com.qianxuefeng.base;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Shawn Qian
 * @since 2018年07月19日
 */
public class ExceptionCatch {
    public static void main(String[] args) {
        try {
//            throw new Throwable("throw");
            throw new RuntimeException("test");
        }catch (Exception e){
            System.out.println("runtime:" + e.getMessage());
            System.out.println("runtime:" + ExceptionUtils.getStackTrace(e));
        } catch (Throwable t) {
            System.out.println("throw:" + t.getMessage());
        }
        System.out.println("finish");

    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

}
package com.qianxuefeng.excel;

/**
 * @author Shawn Qian
 * @since 2018年08月08日
 */
public class ExcelException extends Exception {
    public ExcelException(){
        super();
    }

    public ExcelException(String s){
        super(s);
    }

    public ExcelException(Throwable t){
        super(t);
    }
}
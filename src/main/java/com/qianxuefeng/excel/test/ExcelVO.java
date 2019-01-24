package com.qianxuefeng.excel.test;

import com.qianxuefeng.excel.ColumnHeader;

import java.util.Date;

/**
 * 雇主责任
 * @author Shawn Qian
 * @since 2018年08月08日
 */
public class ExcelVO {
    @ColumnHeader("姓名")
    private String name;
    @ColumnHeader("生日")
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
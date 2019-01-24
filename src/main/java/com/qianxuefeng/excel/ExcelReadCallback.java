package com.qianxuefeng.excel;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

public abstract class ExcelReadCallback<T> {
        int rows = 1;

        public ExcelReadCallback() {
        }

        public ExcelReadCallback(int rows) {
                this.rows = rows;
        }

        int getRows(){
                return rows;
        }

        public void handle(T t) throws InvocationTargetException, IllegalAccessException, ParseException {

        }

        public void batchHandle(List<T> list) throws InvocationTargetException, IllegalAccessException, ParseException {

        }
}
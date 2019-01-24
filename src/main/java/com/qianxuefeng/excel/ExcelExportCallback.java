package com.qianxuefeng.excel;

import java.util.List;

public interface ExcelExportCallback<T> {
        List<T> append(int start, int rows) throws IllegalAccessException, InstantiationException;
}
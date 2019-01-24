package com.qianxuefeng.excel.test;

import com.google.common.collect.Lists;
import com.qianxuefeng.base.FileTest;
import com.qianxuefeng.excel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Shawn Qian
 * @since 2018年08月03日
 */
public class ExcelTest {
    private static final Logger log = LoggerFactory.getLogger(ExcelTest.class);

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/qianxuefeng/Desktop/test2.xlsx";
        String outPath = "/Users/qianxuefeng/Desktop/export.xlsx";
        byte[] bytes = FileTest.read(filePath);
        try {
            final List<ExcelVO> list = Lists.newArrayList();
            ExcelUtils.read(ExcelVO.class, filePath, bytes, new ExcelReadCallback<ExcelVO>() {
                @Override
                public void handle(ExcelVO excelVO) {
                    list.add(excelVO);
                }
            });

            byte[] exportBytes = ExcelUtils.export(ExcelVO.class, new ExcelExportCallback<ExcelVO>(){
                @Override
                public List append(int start, int rows) {
                    list.remove(0);
                    return list;
                }
            });

            FileTest.export(outPath, exportBytes);
            log.error("文件上传成功，后台处理中...");
        } catch (ExcelException e) {
            log.error("Excel解析失败！", e);
        }
    }

}
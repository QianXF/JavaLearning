package com.qianxuefeng.csv;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

/**
 *
 * CSV文件导出工具类
 *
 * Created on 2014-08-07
 * @author
 * @reviewer
 */
public class CsvUtil {

//    public static void main(String[] args) {
//        List<Object> heads = Lists.newArrayList("姓名", "身份证", "保单号", "起始时间", "结束时间");
//
//        List<List<Object>> dataList = Lists.newArrayList(
//                Lists.newArrayList("姓名1", "身份证1", "保单号1", "起始时间1", "结束时间1"),
//                Lists.newArrayList("姓名2", "身份证2", "保单号2", "起始时间2", "结束时间2"),
//                Lists.newArrayList("姓名3", "身份证3", "保单号3", "起始时间3", "结束时间3"),
//                Lists.newArrayList("姓名4", "身份证4", "保单号4", "起始时间4", "结束时间4"),
//                Lists.newArrayList("姓名5", "身份证5", "保单号5", "起始时间5", "结束时间5"));
//
//        createCsvFile(heads, dataList, "/Users/qianxuefeng/Desktop", "123");
//    }

    /**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File createCsvFile(List<Object> head, List<List<Object>> dataList,
                                     String outPutPath, String filename) {

        File csvFile = null;
        FileOutputStream fio = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            byte[] bytes = generateCsv(head, dataList);

            fio = new FileOutputStream(csvFile);
            fio.write(bytes);
            fio.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fio.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    private static byte[] generateCsv(List<Object> head, List<List<Object>> dataList){
        BufferedWriter csvWtriter = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "GB2312"), 1024);
            // 写入文件头部
                writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();

            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
}
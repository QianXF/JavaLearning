package com.qianxuefeng.excel;

import com.google.common.collect.Lists;
import com.qianxuefeng.utils.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shawn Qian
 * @since 2018年08月07日
 */
public class ExcelUtils {

    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     *
     * @param clazz
     * @param callback
     * @return
     */
    public static <T> byte[] export(Class<T> clazz, ExcelExportCallback<T> callback) throws ExcelException {
        Field[] columns = clazz.getDeclaredFields();
        String[] headers = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            Field field = columns[i];
            ColumnHeader columnHeader = field.getAnnotation(ColumnHeader.class);
            if (columnHeader != null) {
                headers[i] = columnHeader.value(); // 中文
            }
        }
        SXSSFWorkbook workbook = null;
        try{
            // 声明一个工作薄
            workbook = new SXSSFWorkbook(1000);//缓存
            workbook.setCompressTempFiles(true);
            //表头样式
            CellStyle headerStyle = headerStyle(workbook);
            // 单元格样式
            CellStyle cellStyle = columnStyle(workbook);
            // 生成一个(带标题)表格
            SXSSFSheet sheet = workbook.createSheet();

            // 生成表头
            SXSSFRow headerRow = sheet.createRow(0); //列头 rowIndex =1
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            if(callback != null) {
                int rowIndex = 1;
                int start = 0;
                int rows = 200;
                List<T> list;
                do {
                    list = callback.append(start, rows);

                    for (T t : list) {
                        SXSSFRow dataRow = sheet.createRow(rowIndex);
                        for (int j = 0; j < columns.length; j++) {
                            SXSSFCell newCell = dataRow.createCell(j);

                            Field field = columns[j];
                            field.setAccessible(true);
                            String value;
                            if (field.get(t) instanceof Date) {
                                value = DateTimeUtil.formatYMD_HR((Date) field.get(t));
                            } else {
                                value = (String) field.get(t);
                            }

                            newCell.setCellValue(value);
                            newCell.setCellStyle(cellStyle);
                        }
                        rowIndex++;
                    }

                    start += rows;
                } while (CollectionUtils.isNotEmpty(list) && list.size() >= rows);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            throw new ExcelException(e);
        } finally {
            try {
                workbook.close();
                workbook.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static CellStyle columnStyle(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(HSSFCellStyle.NO_FILL);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        return cellStyle;
    }

    private static CellStyle headerStyle(SXSSFWorkbook workbook){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(HSSFCellStyle.NO_FILL);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    /**
     * Excel导入
     * @param clazz 反回数据类型
     * @param fileName 文件名
     * @param data 文件数据
     * @param callback 回掉方法
     * @param <T>
     * @return
     */
    public static <T> void read(Class<T> clazz, String fileName, byte [] data, ExcelReadCallback<T> callback) throws ExcelException {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new ByteArrayInputStream(data);

            switch (fileType) {
                case ".xls":
                    wb = new HSSFWorkbook(is);
                    break;
                case ".xlsx":
                    wb = new XSSFWorkbook(is);
                    break;
                default:
                    throw new ExcelException("读取的不是excel文件");
            }

            Map<String, Field> headerMapping = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                ColumnHeader columnHeader = field.getAnnotation(ColumnHeader.class);
                if (columnHeader != null) {
                    headerMapping.put(columnHeader.value(), field);// 中文， 英文
                }
            }

            Sheet sheet = wb.getSheetAt(0);// 只做第一个

            Row r = sheet.getRow(0);
            Field[] headers = new Field[r.getPhysicalNumberOfCells()];
            for (int k = 0; k < r.getPhysicalNumberOfCells(); k++){
                headers[k] = headerMapping.get(r.getCell(k).getStringCellValue());
            }

            int rowSize = sheet.getLastRowNum() + 1;

            List<T> list = Lists.newArrayList();
            for (int j = 1; j < rowSize; j++) {//遍历行
                Row row = sheet.getRow(j);
                if (row == null) {//略过非数据行
                    continue;
                }

                T t = clazz.newInstance();

                boolean ignore = true;
                for (int k = 0; k < headers.length; k++) {
                    Cell cell = row.getCell(k);
                    Field field = headers[k];
                    Object value = null;

                    if (cell != null) {
                        if(XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()){
                            if(field.getType().isAssignableFrom(Date.class)){
                                value = cell.getDateCellValue();
                            }else if(field.getType() == Double.class){
                                value = cell.getNumericCellValue();
                            }else if(field.getType() == Integer.class){
                                value = ((Double)cell.getNumericCellValue()).intValue();
                            }else if(field.getType() == Long.class) {
                                value = ((Double)cell.getNumericCellValue()).longValue();
                            }else{
                                value = cell.getNumericCellValue();
                            }

                            ignore = false;
                        } else if(StringUtils.isNotBlank(cell.getStringCellValue())){
                            value = cell.toString();
                            ignore = false;
                        }


                        field.setAccessible(true);
                        field.set(t, value);
                    }
                }


                if(!ignore && callback != null) {
                    if(callback.getRows() == 1)
                        callback.handle(t);

                    else if(callback.getRows() > 1) {
                        list.add(t);
                        if(list.size() >= callback.getRows() || j == rowSize - 1) {
                            callback.batchHandle(list);
                            list = Lists.newArrayList();
                        }
                    }
                }
            }
        } catch (ParseException | InvocationTargetException | IllegalAccessException | IOException | InstantiationException e) {
            throw new ExcelException(e);
        } finally {
            try {
                if (wb != null) {
                        wb.close();
                }
                if (is != null) {
                        is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
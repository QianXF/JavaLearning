package com.qianxuefeng.base;

import java.io.*;

/**
 * @author Shawn Qian
 * @since 2018年07月17日
 */
public class FileTest {

    public static void main(String[] args) {
        System.out.println(readToString(System.getProperty("user.dir") + "/src/main/resources/data.txt"));
    }

    public static String readResource(String fileName) {
        return readToString(System.getProperty("user.dir") + "/src/main/resources/" + fileName);
    }

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        try {
            return new String(read(fileName), encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] read(String fileName){
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally {
            try {
                in.close();
            }catch (IOException ioe1){
                ioe1.printStackTrace();
            }
        }
        return filecontent;
    }

    public static void export(String fileName, byte[] bytes) throws FileNotFoundException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(fileName);
            os.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
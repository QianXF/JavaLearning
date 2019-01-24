package com.qianxuefeng.jvm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
 
public class PermGenOomMock{
    public static void main(String[] args) {
        URL url = null;
        List<ClassLoader> classLoaderList = new ArrayList<ClassLoader>();
        try {
            url = new File("/Users/qianxuefeng/git/JavaLearning/target/classes/com/jvm").toURI().toURL();
            URL[] urls = {url};
            ClassLoader loader = new URLClassLoader(urls);
            long count = 0;
            while (true){
                count++;
                classLoaderList.add(loader);
                loader.loadClass("Test");
                System.out.println(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
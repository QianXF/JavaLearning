package com.qianxuefeng.zk;

import org.apache.zookeeper.data.Stat;

/**
 * @author Shawn Qian
 * @since 2018年02月13日
 */
public class ZKTest {
    public static void main(String[] args) {

        String host = "120.26.62.83";
        // znode path
        String path = "/MySecondZnode"; // Assign path to znode

        // data in byte array
        byte[] data = "My first zookeeper app".getBytes(); // Declare data
        byte[] updateData = "success".getBytes(); // Declare data

        ZooKeeperConnection conn = new ZooKeeperConnection(host);
        try {
            conn.connect();
            Stat stat = conn.exists(path);
            if(stat != null){
                conn.rmr(path);
            }
            conn.create(path, data); // Create the data to the specified path
            System.out.println(conn.getData(path));//此方法有问题，会卡住流程
            conn.update(path, updateData);
            conn.create(path + "/c1", "c1-data".getBytes()); // Create the data to the specified path
            conn.create(path + "/c2", "c2-data".getBytes()); // Create the data to the specified path
            conn.getChildren(path);
            conn.rmr(path);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
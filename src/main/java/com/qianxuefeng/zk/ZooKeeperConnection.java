package com.qianxuefeng.zk;// import java classes

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZK连接器
 *
 * @author Shawn Qian
 * @since 2018年02月13日
 */
public class ZooKeeperConnection {

    private static final String CHARSET = "UTF-8";
    private static final String ROOT_DIR = "/";

    // declare zookeeper instance to access ZooKeeper ensemble
    private ZooKeeper zk;
//   final CountDownLatch connectedSignal = new CountDownLatch(1);  // create static instance for zookeeper class.

    private String host;

    public ZooKeeperConnection(String host) {
        this.host = host;
    }

    /**
     * 节点是否存在
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat exists(String path) throws
            KeeperException, InterruptedException {
        return zk.exists(path, true);
    }

    public String getData(final String path) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        Stat stat = exists(path);
        final CountDownLatch connectedSignal = new CountDownLatch(1);

        if (stat != null) {
            byte[] b = zk.getData(path, new Watcher() {

                public void process(WatchedEvent we) {

                    if (we.getType() == Event.EventType.None) {
                        switch (we.getState()) {
                            case Expired:
                                connectedSignal.countDown();
                                break;
                        }

                    } else {
                        try {
                            byte[] bn = zk.getData(path,
                                    false, null);
                            String data = new String(bn,
                                    CHARSET);
                            System.out.println(data);
                            connectedSignal.countDown();

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }, null);

            String data = new String(b, CHARSET);
            connectedSignal.await();
            return data;

        } else {
            System.out.println("Node does not exists");
        }

        return null;
    }

    // Method to update the data in a znode. Similar to getData but without watcher.
    public void update(String path, byte[] data) throws
            KeeperException, InterruptedException {
        zk.setData(path, data, zk.exists(path, true).getVersion());
    }

    /**
     * 获取子节点
     *
     * @param path
     * @return
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        Stat stat = exists(path);

        if (stat != null) {
            List<String> children = zk.getChildren(path, false);
            for (String child : children)
                System.out.println(child); //Print children's
            return children;
        } else {
            System.out.println("Node does not exists");
            return null;
        }
    }

    /**
     * 创建节点
     *
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void create(String path, byte[] data) throws
            KeeperException, InterruptedException {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    // Method to check existence of znode and its status, if znode is available.
    public void delete(String path) throws KeeperException, InterruptedException {
        zk.delete(path, zk.exists(path, true).getVersion());
    }

    /**
     * 递归删除 因为zookeeper只允许删除叶子节点，如果要删除非叶子节点，只能使用递归
     * @param path
     * @throws IOException
     */
    public void rmr(String path) throws Exception {
        //获取路径下的节点
        List<String> children = zk.getChildren(path, false);
        for (String pathCd : children) {
            //获取父节点下面的子节点路径
            String newPath;
            //递归调用,判断是否是根节点
            if (path.equals(ROOT_DIR)) {
                newPath = ROOT_DIR + pathCd;
            } else {
                newPath = path + ROOT_DIR + pathCd;
            }
            rmr(newPath);
        }
        //删除节点,并过滤zookeeper节点和 /节点
        if (path != null && !path.trim().startsWith("/zookeeper") && !path.trim().equals(ROOT_DIR)) {
            zk.delete(path, -1);
            //打印删除的节点路径
            System.out.println("被删除的节点为：" + path);
        }
    }


    /**
     * 连接zk
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect() throws IOException, InterruptedException {
        final CountDownLatch connectedSignal = new CountDownLatch(1);

        zk = new ZooKeeper(host, 500, new Watcher() {

            public void process(WatchedEvent we) {

                if (we.getState() == KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            }
        });

        connectedSignal.await();
    }

    /**
     * 关闭zk连接
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        zk.close();
    }
}
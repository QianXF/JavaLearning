package com.qianxuefeng.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPSocketFactory {
    private Socket mSocket;// socket连接对象
    private DataOutputStream out;
    private DataInputStream in;// 输入流
    private byte[] buffer = new byte[1024*1];// 缓冲区字节数组，信息不能大于此缓冲区
    private byte[] tmpBuffer;// 临时缓冲区
    private TCPSocketCallback callback;// 信息回调接口
    private int timeOut = 1000 * 30;

    /*
     * 构造方法传入信息回调接口对象
     *
     *   @param sdi
     *            回调接口
     */
    public TCPSocketFactory(TCPSocketCallback callback) {
        this.callback = callback;
    }

    /**
     *  连接网络服务器
     *
     */
    public void connect(String ip, int port) throws Exception {
        mSocket = new Socket();
        SocketAddress address = new InetSocketAddress(ip, port);
        mSocket.connect(address, timeOut);// 连接指定IP和端口
        if (isConnected()) {
            out = new DataOutputStream(mSocket.getOutputStream());// 获取网络输出流
            in = new DataInputStream(mSocket.getInputStream());// 获取网络输入流
            if (isConnected()) {
                callback.tcp_connected();
            }
        }
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 返回连接服是否成功
     *
     * @return
     */
    public boolean isConnected() {
        if (mSocket == null || mSocket.isClosed()) {
            return false;
        }
        return mSocket.isConnected();
    }

    /**
     *  发送数据
     * @param buffer
     *             信息字节数据
     * @throws IOException
     */
    public void write(byte[] buffer) throws IOException {
        if (out != null) {
            out.write(buffer);
            out.flush();
        }
    }

    /**
     * 断开连接
     * @throws IOException
     */

    public void disconnect() {
        try {
            if (mSocket != null) {
                if (!mSocket.isInputShutdown()) {
                    mSocket.shutdownInput();
                }
                if (!mSocket.isOutputShutdown()) {
                    mSocket.shutdownOutput();
                }
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (mSocket != null && !mSocket.isClosed()) {
                // 判断socket不为空并且是连接状态
                mSocket.close();// 关闭socket
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            callback.tcp_disconnect();
            out = null;
            in = null;
            mSocket = null;// 制空socket对象
        }
    }

    /* * 读取网络数据
     * @throws IOException
     */
    public void read() throws IOException {
        String msg=null;
        if (in != null) {
            int len = 0;// 读取长度
            while ((len = in.read(buffer)) > 0) {
                tmpBuffer = new byte[len];// 创建临时缓冲区
                String t=new String(tmpBuffer);
                System.arraycopy(buffer, 0, tmpBuffer, 0, len);// 将数据拷贝到临时缓冲区
                msg=new String(tmpBuffer,"UTF-8");
                callback.tcp_receive(tmpBuffer);// 调用回调接口传入得到的数据
                tmpBuffer = null;
            }
        }
    }
}

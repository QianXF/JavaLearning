package com.qianxuefeng.socket;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Shawn Qian
 * @since 2018年11月17日
 */
public class TCPSocketMain {
    //tcp连接
    private static Map<String, TCPSocketConnect> connectMap = Maps.newHashMap();

    public static void main(String[] args) {
        for(int i = 0; i < 1; i++) {
            String key = "qianxuefeng" + i;
            TCPSocketConnect connect = OpenTCPClient(key);
            connectMap.put(key, connect);
        }
//
//        while(true) {
//            for (String key : connectMap.keySet()) {
//                TCPSocketConnect connect = connectMap.get(key);
//                String linkcmd = String.format("{\"code\":\"0\",\"guiNo\":\"%s\",\"msg\":\"%s\",\"type\":\"link\"}\r\n", key, DateTimeUtil.formatYMDHMS_HR(new Date()));
//                connect.write(linkcmd.getBytes());
//            }
//        }
    }

    /*
      开启TCPClient连接
     */
    private static TCPSocketConnect OpenTCPClient(String guiNo) {
        final TCPSocketConnect connect = new TCPSocketConnect(new TCPSocketCallback() {
            @Override
            public void tcp_connected() {
                System.out.println(">TCP连接服务器成功<");
            }

            @Override
            public void tcp_receive(byte[] buffer) {
                //接收到服务器指令，这里进行解析处理业务动作
                System.out.println("---" + new String(buffer));
                String str= new String (buffer);
                System.out.println("TCP receive msg:" + str);

//                try {
//                    JSONObject jsonObject = JSON.parseObject(str);
//                    String type = jsonObject.getString("type");
//                    if(type == null){
//                        return;
//                    }
//                    switch (type) {
//                        case "storein":
//                            if (openBoxTcp(str)==true){
//                                String retunstr= new String(buffer);
//                                retunstr = retunstr.replaceAll("\"code\":\"\"","\"code\":\"0\"");
//                                connect.write(retunstr.getBytes());
//                            } else {
//                                String retunstr= new String(buffer);
//                                retunstr = retunstr.replaceAll("\"code\":\"\"","\"code\":\"-1\"");
//                                connect.write(retunstr.getBytes());
//                            }
//
//                            break;
//                        case "takeout":
//                            if (openBoxTcp(str)==true){
//                                String retunstr= new String(buffer);
//                                retunstr = retunstr.replaceAll("\"code\":\"\"","\"code\":\"0\"");
//                                connect.write(retunstr.getBytes());
//                            } else {
//                                String retunstr= new String(buffer);
//                                retunstr = retunstr.replaceAll("\"code\":\"\"","\"code\":\"-1\"");
//                                connect.write(retunstr.getBytes());
//                            };
//
//                            break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }

            private boolean openBoxTcp(String str) {
                try {
                    Thread.sleep(1000);
                    return true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void tcp_disconnect() {
                System.out.println(">TCP连接断开<");
            }
        });


        String SrvIP="127.0.0.1";
//        String SrvIP="120.26.62.83";
        int SrvPort=9226;
        //region 根据编译环境获取TCP通讯服务器地址&端口
        /*
        if (BuildConfig.DEBUG) {
            //本地测试环境
            SrvIP="192.168.31.140";
            SrvPort=8080;
        } else {
            //生产环境
            SrvIP="106.15.176.115";
            SrvPort=9226;
        }
        */
        //endregion

        connect.setAddress(SrvIP, SrvPort);
        connect.setGuiNo(guiNo);
        new Thread(connect).start();

        return connect;
    }
}
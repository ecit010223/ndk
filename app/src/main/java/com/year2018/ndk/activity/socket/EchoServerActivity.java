package com.year2018.ndk.activity.socket;

import android.content.Context;
import android.content.Intent;

import com.year2018.ndk.R;

/**
 * Author: zyh
 * Date: 2018/9/7 17:25
 */
public class EchoServerActivity extends AbstractEchoActivity {

    public static final void entry(Context from){
        Intent intent = new Intent(from,EchoServerActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_echo_server;
    }

    //    public EchoServerActivity() {
//        super(R.layout.activity_echo_server);
//    }

    @Override
    protected void onStartButtonClicked() {
        Integer port = getPort();
        if(port != null){
            ServerTask serverTask = new ServerTask(port);
            serverTask.start();
        }
    }

    /**
     * 根据给定端口启动TCP服务器
     * @param port 端口号
     * @throws Exception
     */
    private native void nativeStartTcpServer(int port) throws Exception;

    /**
     * 根据给定端口启动UDP服务
     * @param port 端口号
     * @throws Exception
     */
    private native void nativeStartUdpServer(int port) throws Exception;

    /**
     * 服务器端任务
     */
    private class ServerTask extends AbstractEchoTask{
        /** 端口号 **/
        private final int port;

        public ServerTask(int port){
            this.port = port;
        }
        @Override
        protected void onBackground() {
            logMessage("Starting server.");
            try{
//                nativeStartTcpServer(port);
                nativeStartUdpServer(port);
            }catch (Exception e){
                logMessage(e.getMessage());
            }
            logMessage("Server terminated.");
        }
    }
}

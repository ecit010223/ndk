package com.year2018.ndk.activity.socket;

import android.content.Context;
import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.widget.EditText;

import com.year2018.ndk.R;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author: zyh
 * Date: 2018/9/10 16:41
 * 在同一设备上的两个应用程序之间或者原生代码与Java层之间建立本地通信通道。
 */
public class EchoLocalActivity extends AbstractEchoActivity {
    /** 消息编辑 **/
    private EditText mEtMessage;

    public static final void entry(Context from){
        Intent intent = new Intent(from,EchoLocalActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_echo_local;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEtMessage = findViewById(R.id.et_echo_message);
    }

    @Override
    protected void onStartButtonClicked() {
        String name = mEtPort.getText().toString();
        String message = mEtMessage.getText().toString();
        if((name.length()>0)&&(message.length()>0)){
            String socketName;
            //如果是filesystem socket，预先准备应用程序的文件目录
            if(isFilesystemSocket(name)){
                //getFilesDir()方法用于获取/data/data/<application package>/files目录
                File file = new File(getFilesDir(),name);
                socketName = file.getAbsolutePath();
            }else{
                socketName = name;
            }
            ServerTask serverTask = new ServerTask(socketName);
            serverTask.start();
            ClientTask clientTask = new ClientTask(socketName,message);
            clientTask.start();
        }
    }

    /**
     * 检查名称是否是filesystem socket
     * @param name socket名称
     * @return filesystem socket
     */
    private boolean isFilesystemSocket(String name){
        return name.startsWith("/");
    }

    /**
     * 启动绑定到给定名称的本地UNIX socket服务器
     * @param name socket名称
     * @throws Exception
     */
    private native void nativeStartLocalServer(String name) throws Exception;

    /**
     * 启动本地UNIX socket客户端
     * @param name 端口号
     * @param message  消息文本
     * @throws Exception
     */
    private void startLocalClient(String name,String message) throws Exception{
        //构造一个本地socket
        LocalSocket clientSocket = new LocalSocket();
        try{
            //设置socket名称空间
            LocalSocketAddress.Namespace namespace;
            if(isFilesystemSocket(name)){
                namespace = LocalSocketAddress.Namespace.FILESYSTEM;
            }else{
                namespace = LocalSocketAddress.Namespace.ABSTRACT;
            }
            //构造本地socket地址
            LocalSocketAddress address = new LocalSocketAddress(name,namespace);
            //连接到本地socket
            logMessage("Connecting to "+name);
            clientSocket.connect(address);
            logMessage("Connected.");
            //以字节形式获取消息
            byte[] messageBytes = message.getBytes();
            //发送消息字节到socket
            logMessage("Sending to the socket...");
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(messageBytes);
            logMessage(String.format("Send %d bytes: %s",messageBytes.length,message));
            //从socket中接收消息返回
            logMessage("Receiving from the socket...");
            InputStream inputStream = clientSocket.getInputStream();
            int readSize = inputStream.read(messageBytes);
            String receiveMessage = new String(messageBytes,0,readSize);
            logMessage(String.format("Received %d bytes:%s",readSize,receiveMessage));
            //关闭流
            outputStream.close();
            inputStream.close();
        }finally{
            //关闭本地socket
            clientSocket.close();
        }
    }

    /**
     * 服务器任务
     */
    private class ServerTask extends AbstractEchoTask{
        /** Socket名称 **/
        private final String name;

        public ServerTask(String name){
            this.name = name;
        }

        @Override
        protected void onBackground() {
            logMessage("Starting server.");
            try{
                nativeStartLocalServer(name);
            }catch (Exception e){
                logMessage(e.getMessage());
            }
            logMessage("Server terminated.");
        }
    }

    /**
     * 客户端任务
     */
    private class ClientTask extends Thread{
        /** Socket名称 **/
        private final String name;

        /** 发送的消息文本 **/
        private final String message;

        public ClientTask(String name,String message){
            this.name = name;
            this.message = message;
        }

        @Override
        public void run() {
            logMessage("Starting client.");
            try{
                startLocalClient(name,message);
            }catch (Exception e){
                logMessage(e.getMessage());
            }
            logMessage("Client terminated.");
        }
    }
}

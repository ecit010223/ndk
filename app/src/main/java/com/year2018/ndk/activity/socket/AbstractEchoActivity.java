package com.year2018.ndk.activity.socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.year2018.ndk.R;

/**
 * Author: zyh
 * Date: 2018/9/7 17:08
 */
public abstract class AbstractEchoActivity extends Activity implements View.OnClickListener {
    /** 布局ID **/
//    private final int mLayoutID;
    /** 端口号 **/
    protected EditText mEtPort;
    /** 服务按钮 **/
    protected Button mBtnStart;
    /** 日志滚动 **/
    protected ScrollView mSvLog;
    /** 日志视图 **/
    protected TextView mTvLog;

//    public AbstractEchoActivity(int layoutID){
//        this.mLayoutID = layoutID;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        mEtPort = findViewById(R.id.et_echo_port);
        mBtnStart = findViewById(R.id.btn_echo_start);
        mBtnStart.setOnClickListener(this);
        mSvLog = findViewById(R.id.sv_echo_log);
        mTvLog = findViewById(R.id.tv_echo_log);
    }

    protected abstract int getLayoutID();

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_echo_start:
                onStartButtonClicked();
                break;
        }
    }

    /** 单击开始按钮 **/
    protected abstract void onStartButtonClicked();

    /** 以整型获取端口号 **/
    protected Integer getPort(){
        Integer port;
        try{
            port = Integer.valueOf(mEtPort.getText().toString());
        }catch (NumberFormatException e){
            port = null;
        }
        return port;
    }

    /**
     * 记录给定的消息
     * @param message 日志消息
     */
    protected void logMessage(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logMessageDirect(message);
            }
        });
    }

    /**
     * 直接记录给定的消息
     * @param message 日志消息
     */
    protected void logMessageDirect(final String message){
        mTvLog.append(message);
        mTvLog.append("\n");
        mSvLog.fullScroll(View.FOCUS_DOWN);
    }

    /**
     * 抽象异步echo任务
     */
    protected abstract class AbstractEchoTask extends Thread{
        /** Handler对象.**/
        private final Handler handler;

        public AbstractEchoTask(){
            handler = new Handler(Looper.getMainLooper());
        }

        /** 在调用线程中先执行回调 **/
        protected void onPreExecute(){
            mBtnStart.setEnabled(false);
            mTvLog.setText("");
        }

        public synchronized void start(){
            onPreExecute();
            super.start();
        }

        @Override
        public void run() {
            onBackground();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute();
                }
            });
        }

        /** 新线程中的背景回调 **/
        protected abstract void onBackground();

        /** 在调用线程后执行回调 **/
        protected void onPostExecute(){
            mBtnStart.setEnabled(true);
        }
    }
    static {
        System.loadLibrary("socket-lib");
    }
}

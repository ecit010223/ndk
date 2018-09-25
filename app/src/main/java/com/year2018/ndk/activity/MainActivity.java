package com.year2018.ndk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.year2018.ndk.R;
import com.year2018.ndk.activity.graphic.TranscodeActivity;
import com.year2018.ndk.activity.socket.EchoClientActivity;
import com.year2018.ndk.activity.socket.EchoLocalActivity;
import com.year2018.ndk.activity.socket.EchoServerActivity;

/**
 * Author: zyh
 * Date: 2018/9/4 8:57
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        findViewById(R.id.btn_main_imooc).setOnClickListener(this);
        findViewById(R.id.btn_main_thread).setOnClickListener(this);
        findViewById(R.id.btn_main_socket_local).setOnClickListener(this);
        findViewById(R.id.btn_main_socket_server).setOnClickListener(this);
        findViewById(R.id.btn_main_socket_client).setOnClickListener(this);
        findViewById(R.id.btn_main_transcode).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_main_imooc:
                ImoocActivity.entry(this);
                break;
            case R.id.btn_main_thread:
                ThreadActivity.entry(this);
                break;
            case R.id.btn_main_socket_local:
                EchoLocalActivity.entry(this);
                break;
            case R.id.btn_main_socket_server:
                EchoServerActivity.entry(this);
                break;
            case R.id.btn_main_socket_client:
                EchoClientActivity.entry(this);
                break;
            case R.id.btn_main_transcode:
                TranscodeActivity.entry(this);
                break;
        }
    }
}

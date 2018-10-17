package com.year2018.ndk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.year2018.ndk.Constant;
import com.year2018.ndk.R;
import com.year2018.ndk.activity.graphic.AviActivity;
import com.year2018.ndk.activity.socket.EchoClientActivity;
import com.year2018.ndk.activity.socket.EchoLocalActivity;
import com.year2018.ndk.activity.socket.EchoServerActivity;
import com.year2018.ndk.activity.wav.WavActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zyh
 * Date: 2018/9/4 8:57
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final String[] REQUEST_PERMISSIONS = new String[]{
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };
    private List<String> missingPermissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermission();
        initUI();
    }

    private void checkAndRequestPermission(){
        for(String requestPermission:REQUEST_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this,requestPermission)!= PackageManager.PERMISSION_GRANTED){
                missingPermissions.add(requestPermission);
            }
        }
        if(!missingPermissions.isEmpty()&& Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(this,
                    missingPermissions.toArray(new String[missingPermissions.size()]),
                    Constant.REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==Constant.REQUEST_PERMISSION_CODE){
            for (int i=grantResults.length-1;i>=0;i--){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    missingPermissions.remove(permissions[i]);
                }
            }
        }
    }

    private void initUI(){
        findViewById(R.id.btn_main_imooc).setOnClickListener(this);
        findViewById(R.id.btn_main_thread).setOnClickListener(this);
        findViewById(R.id.btn_main_socket_local).setOnClickListener(this);
        findViewById(R.id.btn_main_socket_server).setOnClickListener(this);
        findViewById(R.id.btn_main_socket_client).setOnClickListener(this);
        findViewById(R.id.btn_main_avi).setOnClickListener(this);
        findViewById(R.id.btn_main_wav).setOnClickListener(this);
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
            case R.id.btn_main_avi:
                AviActivity.entry(this);
                break;
            case R.id.btn_main_wav:
                WavActivity.entry(this);
                break;
        }
    }
}

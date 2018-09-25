package com.year2018.ndk.activity.graphic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.year2018.ndk.Constant;
import com.year2018.ndk.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: zyh
 * Date: 2018/9/19 9:02
 */
public class TranscodeActivity extends Activity implements View.OnClickListener{
    /** AVI文件名字 */
    private EditText etTranscodeFileName;
    /** Player类型的单选组 */
    private RadioGroup rgTranscode;

    private static final String[] REQUEST_PERMISSIONS = new String[]{
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private List<String> missingPermissions = new ArrayList<>();

    public static final void entry(Context from){
        Intent intent = new Intent(from,TranscodeActivity.class);
        from.startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndRequestPermission();
        setContentView(R.layout.activity_transcode);

        etTranscodeFileName = (EditText) findViewById(R.id.et_transcode_file_name);
        rgTranscode = (RadioGroup) findViewById(R.id.rg_transcode);
        findViewById(R.id.btn_transcode_play).setOnClickListener(this);
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
//        if(missingPermissions.isEmpty()){
//
//        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_transcode_play:
                onPlayButtonClick();
                break;
        }
    }

    private void onPlayButtonClick() {
        Intent intent;
        // 获得选择的单选按钮的id
        int radioId = rgTranscode.getCheckedRadioButtonId();
        switch (radioId) {
            case R.id.rb_transcode_bitmap_player:
                intent = new Intent(this, BitmapPlayerActivity.class);
                break;
            case R.id.rb_transcode_openGL_player:
                intent = new Intent(this,OpenGLPlayerActivity.class);
                break;
            case R.id.rb_transcode_native_player:
                intent = new Intent(this,NativeWindowPlayerActivity.class);
                break;
            default:
                throw new UnsupportedOperationException("radioId=" + radioId);
        }
        // 基于外部存储器
        File file = new File(Environment.getExternalStorageDirectory(), etTranscodeFileName.getText().toString());
        if(file.exists()){
            // 将AVI文件名字作为extra内容
            intent.putExtra(AbstractPlayerActivity.EXTRA_FILE_NAME,file.getAbsolutePath());
            // 启动player activity
            startActivity(intent);
        }else{
            Toast.makeText(this,"视频文件不存在",Toast.LENGTH_LONG).show();
        }
    }
}

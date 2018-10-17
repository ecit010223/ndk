package com.year2018.ndk.activity.graphic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.year2018.ndk.R;

import java.io.File;

/**
 * Author: zyh
 * Date: 2018/9/19 9:02
 */
public class AviActivity extends Activity implements View.OnClickListener{
    /** AVI文件名字 */
    private EditText etAviFilename;
    /** Player类型的单选组 */
    private RadioGroup rgAvi;

    public static final void entry(Context from){
        Intent intent = new Intent(from,AviActivity.class);
        from.startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avi);

        etAviFilename = (EditText) findViewById(R.id.et_avi_filename);
        rgAvi = (RadioGroup) findViewById(R.id.rg_avi);
        findViewById(R.id.btn_avi_play).setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_avi_play:
                onPlayButtonClick();
                break;
        }
    }

    private void onPlayButtonClick() {
        Intent intent;
        // 获得选择的单选按钮的id
        int radioId = rgAvi.getCheckedRadioButtonId();
        switch (radioId) {
            case R.id.rb_avi_bitmap_player:
                intent = new Intent(this, BitmapPlayerActivity.class);
                break;
            case R.id.rb_avi_openGL_player:
                intent = new Intent(this,OpenGLPlayerActivity.class);
                break;
            case R.id.rb_avi_native_player:
                intent = new Intent(this,NativeWindowPlayerActivity.class);
                break;
            default:
                throw new UnsupportedOperationException("radioId=" + radioId);
        }
        // 基于外部存储器,小米存放在内部存储设置根目录下
        File file = new File(Environment.getExternalStorageDirectory(), etAviFilename.getText().toString());
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

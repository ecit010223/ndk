package com.year2018.ndk.activity.wav;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.year2018.ndk.R;

import java.io.File;
import java.io.IOException;

/**
 * Author: zyh
 * Date: 2018/10/17 15:13
 * wave播放器主activity
 */
public class WavActivity  extends Activity implements View.OnClickListener {
    /** 文件名编辑 **/
    private EditText mEtFilename;

    public static void entry(Context from){
        Intent intent = new Intent(from,WavActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wav);
        mEtFilename = (EditText)findViewById(R.id.et_wav_filename);
        findViewById(R.id.btn_wav_play).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_wav_play:
                playWav();
                break;
        }
    }

    private void playWav(){
        //位于外部存储器
        File file = new File(Environment.getExternalStorageDirectory(),
                mEtFilename.getText().toString());
        //开始播放
        PlayTask playTask = new PlayTask();
        playTask.execute(file.getAbsolutePath());
    }

    //播放任务
    private class PlayTask extends AsyncTask<String,Void,Exception>{

        /**
         * 后台播放任务
         * @param file
         * @return
         */
        @Override
        protected Exception doInBackground(String... file) {
            Exception result = null;
            try {
                // 播放WAVE文件
                play(file[0]);
            }catch (IOException ex){
                result = ex;
            }
            return result;
        }

        /**
         * 执行Post
         * @param ex
         */
        @Override
        protected void onPostExecute(Exception ex) {
            //如果播放失败则显示错误信息
            if(ex!=null){
                new AlertDialog.Builder(WavActivity.this)
                        .setTitle("错误警告")
                        .setMessage(ex.getMessage())
                        .show();
            }
        }
    }

    /**
     * 使用原生API播放指定的WAVE文件
     * @param filename
     * @throws IOException
     */
    private native void play(String filename) throws IOException;

    static {
        System.loadLibrary("wav-lib");
    }
}

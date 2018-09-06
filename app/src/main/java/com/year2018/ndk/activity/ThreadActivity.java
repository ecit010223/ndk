package com.year2018.ndk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.year2018.ndk.R;

/**
 * Author: zyh
 * Date: 2018/9/6 15:05
 */
public class ThreadActivity extends Activity {
    private EditText mEtThreadCount;
    private EditText mEtIterationCount;
    private TextView mTvLog;

    public static final void entry(Context from){
        Intent intent = new Intent(from,ThreadActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        // 初始化原生代码
        nativeInit();

        mEtThreadCount = findViewById(R.id.et_thread_count);
        mEtIterationCount = findViewById(R.id.et_thread_iteration_count);
        mTvLog = findViewById(R.id.tv_thread_log);

        findViewById(R.id.btn_thread_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int threads = getNumber(mEtThreadCount,0);
                int iterations = getNumber(mEtIterationCount,0);
                if(threads>0&&iterations>0){
                    startThreads(threads,iterations);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 释放原生资源
        nativeFree();
        super.onDestroy();
    }

    /**
     * 原生消息回调
     *
     * @param message 原生消息
     */
    private void onNativeMessage(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                mTvLog.append(message);
                mTvLog.append("\n");
            }
        });
    }

    /**
     * 以integer格式获取编辑文本的值，如果值为空或计数不能分析，则返回默认值
     */
    private static int getNumber(EditText editText, int defaultValue) {
        int value;

        try {
            value = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * 启动给定个数的线程进行迭代
     */
    private void startThreads(int threads, int iterations) {
        posixThreads(threads, iterations);
    }

    /**
     * 初始化原生代码
     */
    private native void nativeInit();

    /**
     * 释放原生资源
     */
    private native void nativeFree();

    /**
     * Native worker.
     *
     * @param id worker id.
     * @param iterations iteration count.
     */
    private native void nativeWorker(int id, int iterations);

    /**
     * Using the POSIX threads.
     *
     * @param threads thread count.
     * @param iterations iteration count.
     */
    private native void posixThreads(int threads, int iterations);

    /**
     * Using Java based threads.
     *
     * @param threads thread count.
     * @param iterations iteration count.
     */
    private void javaThreads(int threads, final int iterations) {
        // Create a Java based thread for each worker
        for (int i = 0; i < threads; i++) {
            final int id = i;

            Thread thread = new Thread() {
                public void run() {
                    nativeWorker(id, iterations);
                }
            };

            thread.start();
        }
    }

    static {
        System.loadLibrary("thread-lib");
    }
}

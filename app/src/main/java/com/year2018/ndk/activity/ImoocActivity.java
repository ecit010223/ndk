package com.year2018.ndk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.year2018.ndk.Constant;
import com.year2018.ndk.R;
import com.year2018.ndk.jniNative.Imooc;

/**
 * Author: zyh
 * Date: 2018/9/4 9:13
 */
public class ImoocActivity extends Activity {
    private TextView mTvMsg;
    private Imooc mImooc;

    static {
        System.loadLibrary("native-lib");
    }

    public static final void entry(Context from){
        Intent intent = new Intent(from,ImoocActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imooc);
        mImooc = new Imooc();
        initUI();
    }

    private void initUI(){
        mTvMsg = (TextView)findViewById(R.id.tv_imooc_msg);
        mTvMsg.setText(mImooc.stringFromJNI());
        Imooc.callStaticMethod(21);
    }

    private static void logMessage(String data){
        Log.d(Constant.TAG,data);
    }

    public static void staticMethod(String data){
        logMessage(data);
    }
}

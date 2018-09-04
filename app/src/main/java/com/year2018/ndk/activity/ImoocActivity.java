package com.year2018.ndk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.year2018.ndk.R;
import com.year2018.ndk.jniNative.Dynamic;
import com.year2018.ndk.jniNative.Imooc;

/**
 * Author: zyh
 * Date: 2018/9/4 9:13
 */
public class ImoocActivity extends Activity {
    private Imooc mImooc;
    private StringBuffer mStringBuffer = new StringBuffer();

    static {
        System.loadLibrary("imooc-lib");
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
//        mStringBuffer.append(mImooc.stringFromJNI()+"\n");
//        Imooc.callStaticMethod(21);
//
//        mStringBuffer.append(General.getPkgName(ImoocActivity.this)+"\n");
//        mStringBuffer.append(Dynamic.dynamicFromJNI()+"\n");
//        mStringBuffer.append(Dynamic.logFromJni()+"\n");

        Dynamic.typeTransform();

        ((TextView)findViewById(R.id.tv_imooc_msg)).setText(mStringBuffer);
    }
}

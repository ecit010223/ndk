package com.year2018.ndk.jniNative.swig.c2j;

import android.widget.TextView;

/**
 * Author: zyh
 * Date: 2018/9/6 8:23
 * 重写AsyncUidProvider类
 */
public class UidHandler extends AsyncUidProvider {
    private final TextView mTextView;

    public UidHandler(TextView textView){
        this.mTextView = textView;
    }

    @Override
    public void onUid(long uid) {
        mTextView.setText("UID:"+uid);
    }
}

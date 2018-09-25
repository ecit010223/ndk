package com.year2018.ndk;

import android.app.Application;

import com.year2018.ndk.util.FileManager;
import com.year2018.ndk.util.ToastManager;

/**
 * Author: zyh
 * Date: 2018/9/4 8:54
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        FileManager.getInstance().init(this);
        ToastManager.getInstance().init(this);
    }
}

package com.year2018.ndk.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

/**
 * Author: zyh
 * Date: 2018/9/20 13:26
 */
public class ToastManager {
    private Application mApplication;

    private Handler mUIHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Toast.LENGTH_SHORT:
                case Toast.LENGTH_LONG:
                    showToast(msg.obj.toString(),msg.what);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ToastManager(){}

    private static class ToastManagerHolder{
        private static final ToastManager instance = new ToastManager();
    }

    public static final ToastManager getInstance(){
        return ToastManagerHolder.instance;
    }

    public final void init(Application application){
        this.mApplication = application;
    }

    public final void toastShort(final String msg){
        mUIHandler.sendMessage(mUIHandler.obtainMessage(Toast.LENGTH_SHORT,msg));
    }

    public final void toastShort(final int msgId){
        mUIHandler.sendMessage(mUIHandler.obtainMessage(Toast.LENGTH_SHORT,mApplication.getString(msgId)));
    }

    public final void toastLong(final String msg){
        mUIHandler.sendMessage(mUIHandler.obtainMessage(Toast.LENGTH_LONG,msg));
    }

    public final void toastLong(final int msgId){
        mUIHandler.sendMessage(mUIHandler.obtainMessage(Toast.LENGTH_LONG,mApplication.getString(msgId)));
    }

    private final void showToast(final String msg,final int duration){
        Toast.makeText(mApplication,msg,duration).show();
    }
}

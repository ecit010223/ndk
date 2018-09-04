package com.year2018.ndk.jniNative;

/**
 * Author: zyh
 * Date: 2018/9/4 9:31
 */
public class Imooc {
    public native String stringFromJNI();

    public static native void callStaticMethod(int i);

    public static native void callStaticMethod(long i,String str);

    public native void callInstanceMethod(int i);

    public native void callInstanceMethod(String str,long i);
}

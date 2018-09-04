package com.year2018.ndk.jniNative;

/**
 * Author: zyh
 * Date: 2018/9/4 14:15
 * 不需要生成头文件，动态注册
 */
public class Dynamic {

    public static native String dynamicFromJNI();

    public static native String logFromJni();

    public static native void typeTransform();

    /**
     * 抛出异常
     * @throws NullPointerException
     */
    private void throwingMethod() throws NullPointerException{
        throw new NullPointerException("Null pointer");
    }

    private native void accessMethod();
}

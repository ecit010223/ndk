/*
 *  Created by zyh on 2018/9/19.
 */
#include "Common.h"

/**
 * Throws a new exception using the given exception class
 * and exception message.
 *
 * @param env JNIEnv interface.
 * @param className class name.
 * @param message exception message.
 */
void ThrowException(
        JNIEnv* env,
        const char* className,
        const char* message){
    // 获取异常类
    jclass clazz = env->FindClass(className);

    // 如果找到异常类
    if (0 != clazz)
    {
        // 抛出异常
        env->ThrowNew(clazz, message);

        // 释放本地类引用
        env->DeleteLocalRef(clazz);
    }
}

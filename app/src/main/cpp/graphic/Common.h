/*
 *  Created by zyh on 2018/9/19.
 */

#ifndef NDK_COMMON_H
#define NDK_COMMON_H

#include <jni.h>

/**
 * 使用给定的异常类和异常信息抛出一个新的异常
 *
 * @param env JNIEnv interface.
 * @param className class name.
 * @param message exception message.
 */
void ThrowException(
        JNIEnv* env,
        const char* className,
        const char* message);

#endif //NDK_COMMON_H

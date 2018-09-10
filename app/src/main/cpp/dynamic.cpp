/*
 *  Created by zyh on 2018/9/4.
 */

#include <jni.h>
#include <android/log.h>
#include "demo_log.h"
#include "basic/refer.h"

/**
 * C++层 native函数
 * @param env
 * @param cls
 * @return
 */
jstring native_hello(JNIEnv *env,jclass cls){
    __android_log_write(ANDROID_LOG_DEBUG,"alex","dynamic native_hello");
    return env->NewStringUTF("native hello from dynamic.cpp native_hello()");
}

/**
 * JNINativeMethod由三部分组成,可添加多组对应:
 * (1)Java中的函数名;
 * (2)函数签名,格式为（输入参数类型）返回值类型;
 *    ()Ljava/lang/String;
 *    ()：表示无参，
 *    Ljava/lang/String; ：表示返回String，在对象类名（包括包名，‘/’间隔）前面加L，分号结尾
 * (3)是函数指针，指向C函数
 */
static JNINativeMethod jniMethods[] = {
        {
                "dynamicFromJNI",    //java层的函数名
                "()Ljava/lang/String;",     //java层函数的参数类型与返回值类型标识
                (void *) native_hello       //native层对应的函数
        },
        {
                "logFromJni",
                "()Ljava/lang/String;",
                (void *)native_log
        },
        {
                "basicJniStatic",
                "()V",
                (void *)basicJniStatic
        },
        {
                "basicJni",
                "()V",
                (void *)basicJni
        }
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm,void *reserved){
    JNIEnv *env = NULL;
    jint result = JNI_FALSE;

    //获取env指针
    if (jvm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }
    if (env == NULL) {
        return result;
    }
    //获取类引用，写类的全路径（包名+类名）。
    jclass clazz = env->FindClass("com/year2018/ndk/jniNative/Dynamic");
    if (clazz == NULL) {
        return result;
    }
    //注册方法
    if (env->RegisterNatives(clazz, jniMethods, sizeof(jniMethods) / sizeof(jniMethods[0])) < 0) {
        return result;
    }
    //成功
    result = JNI_VERSION_1_6;
    return result;
}


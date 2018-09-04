/*
 *  Created by zyh on 2018/9/4.
 */
#include <stdio.h>
#include <malloc.h>
#include "refer.h"
#include "../full_log.h"

/**
 * 将java字符串转换成C字符串
 * @param env
 * @param cls
 */
void jstring_to_c(JNIEnv *env,jclass cls){
    const char * str;
    jboolean isCopy = JNI_TRUE;
    jstring jstr = env->NewStringUTF("java string");
    str = env->GetStringUTFChars(jstr,&isCopy);
    if(str!=0){
        printf("Java string: %s",str);
        if (isCopy==JNI_TRUE){
            LOGD("C string is a copy of the java string.");
        }else{
            LOGD("C string points to actual string.");
        }
    }
    env->ReleaseStringUTFChars(jstr,str);
}

void array_operate(JNIEnv *env,jclass cls){
    jintArray jIntArr;
    jIntArr = env->NewIntArray(10);
    jint* arr;
    if(jIntArr!=0){
        MY_LOG_DEBUG("create jintArray success");
        // 将java数组复制到给定的C数组中
        env->GetIntArrayRegion(jIntArr,0,10,arr);
        //对arr进行操作
        for(int i= 0;i<10;i++){
            arr[i]=i+1;
        }
        // 将C数组复制回Java数组
        env->SetIntArrayRegion(jIntArr,0,10,arr);
    }
//    env->ReleaseIntArrayElements(jIntArr,arr,JNI_ABORT);
}

/**
 * 原生I/O在缓冲管理区、大规模网络和文件I/O及字符集支持方面的性能有所改进。
 * @param env
 * @param cls
 */
void nio_operator(JNIEnv* env,jclass cls){
    //创建字节缓冲区
    unsigned char* buffer = (unsigned char*)malloc(1024);
    jobject directBuffer;
    directBuffer = env->NewDirectByteBuffer(buffer,1024);
    directBuffer=NULL;
}

void exception_operator(JNIEnv* env,jclass cls){
    jthrowable ex;
    jmethodID throwingMethodID = env->GetStaticMethodID(cls,"throwingMethod","()V");
    env->CallStaticVoidMethod(cls,throwingMethodID);
    ex = env->ExceptionOccurred();
    if(ex!=0){
        env->ExceptionClear();
    }
    env->DeleteLocalRef(ex);

    //抛出异常
    jclass nullPointException = env->FindClass("java/lang/NullPointerException");
    if(nullPointException!=0){
        env->ThrowNew(nullPointException,"exception msg");
    }
    env->DeleteLocalRef(nullPointException);
}

void type_transform(JNIEnv *env,jclass cls){
    jstring_to_c(env,cls);
    array_operate(env,cls);
    nio_operator(env,cls);
//    exception_operator(env,cls);
}






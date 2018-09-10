/*
 *  Created by zyh on 2018/9/4.
 */
#include <stdio.h>
#include <malloc.h>
#include "refer.h"
#include "../full_log.h"


jobject globalObj;
jobject weakGlobalObj;

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

/**
 * 访问域
 * 在内存溢出的情况下，该函数内的函数均返回NULL，且代码不会继续执行
 * @param env
 * @param obj
 */
void field_operator(JNIEnv* env,jobject obj){
    jclass cls = env->GetObjectClass(obj);
    //获取实例域的域ID
    jfieldID instanceFieldId = env->GetFieldID(cls,"instanceFiled","Ljava/lang/String;");
    //获取静态域的域ID
    jfieldID staticFieldID = env->GetStaticFieldID(cls,"staticField","Ljava/lang/String;");
    //获取实例域
    jobject instanceField = env->GetObjectField(obj,instanceFieldId);
    //获取静态域
    jobject staticField = env->GetStaticObjectField(cls,staticFieldID);
}

/**
 * 全局引用和弱全局引用
 * @param env
 * @param cls
 */
void global_operator(JNIEnv *env,jobject obj){
    // 用给定的局部引用创建全局引用
    globalObj = env->NewGlobalRef(obj);

    //删除全局引用
    env->DeleteGlobalRef(globalObj);

    //用给定的局部引用创建弱全局引用
    weakGlobalObj = env->NewWeakGlobalRef(obj);
    //检验弱全局变量是否仍然有效
    if(JNI_FALSE==env->IsSameObject(weakGlobalObj,NULL)){
        //对象仍然处于活动状态且可以使用
        //...
        //删除弱全局引用
        env->DeleteWeakGlobalRef(weakGlobalObj);
    }else{
        //对象被垃圾回收器收回，不能使用
    }
}

/** java中static native方法在native层对就方法的第二个参数类型为jclass **/
void basicJniStatic(JNIEnv *env,jclass cls){
    jstring_to_c(env,cls);
    array_operate(env,cls);
    nio_operator(env,cls);
//    exception_operator(env,cls);
}

/** java中static native方法在native层对就方法的第二个参数类型为jobject **/
void basicJni(JNIEnv *env,jobject obj){
    field_operator(env,obj);
    global_operator(env,obj);
}






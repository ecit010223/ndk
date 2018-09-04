/*
 *  Created by zyh on 2018/9/4.
 */
#include <jni.h>
#include <string>
#include "simple_log.h"
#include "com_year2018_ndk_jniNative_General.h"

/*
 * Class:     com_year2018_ndk_jniNative_General
 * Method:    getPkgName
 * Signature: (Ljava/lang/Object;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_year2018_ndk_jniNative_General_getPkgName
        (JNIEnv *env, jclass cls, jobject obj){
    jclass jObjCls = env->GetObjectClass(obj);
    jmethodID jMtd = env->GetMethodID(jObjCls,"getPackageName","()Ljava/lang/String;");
    jstring jPkgName = static_cast<jstring>(env->CallObjectMethod(obj,jMtd));
    return jPkgName;
}


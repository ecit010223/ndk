/*
 *  Created by zyh on 2018/9/4.
 */

#include <jni.h>
#include <string>
#include "simple_log.h"
#include "com_year2018_ndk_jniNative_Imooc.h"


/*
 * Class:     com_year2018_ndk_jniNative_Imooc
 * Method:    stringFromJNI
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_year2018_ndk_jniNative_Imooc_stringFromJNI(
        JNIEnv *env,
        jobject /* this */){
    LOGV("from C log verbose");
    LOGD("from C log debug");
    LOGI("from C log info");
    std::string hello = "Hello from C++ stringFromJNI";
    return env->NewStringUTF(hello.c_str());
}

/*
 * Class:     com_year2018_ndk_jniNative_Imooc
 * Method:    callStaticMethod
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_jniNative_Imooc_callStaticMethod__I
        (JNIEnv *env, jclass jclass1, jint jint1){
    jclass clsImooc = env->FindClass("com/year2018/ndk/jniNative/Imooc");
    if(clsImooc==NULL){
        return;
    }
    jmethodID mtdStaticMethod = env->GetStaticMethodID(clsImooc,"staticMethod",
                                                       "(Ljava/lang/String;)V");
    if(mtdStaticMethod==NULL){
        return;
    }

    jstring data = env->NewStringUTF("Imooc_callStaticMethod__I called staticMethod");
    if(data==NULL){
        return;
    }
    env->CallStaticVoidMethod(clsImooc,mtdStaticMethod,data);
    // 大多数JNI函数返回局部引用。局部引用不能在后续的调用中被缓存及重用，主要因为它们的使用期限
    // 仅限于原生方法，一旦原生函数返回，局部引用即被释放，也可以用DeleteLocalRef函数显式释放原
    // 生代码。
    // 根据jni的规范，虚拟机应该允许原生代码创建最少16个局部引用。在单个方法调用时进行多个内存
    // 密集型操作的最佳实践是删除未用的局部引用。如果不能，原生代码可以在使用之前用
    // EnsureLocalCapacity方法请求更多的局部引用槽。
    env->DeleteLocalRef(clsImooc);
    env->DeleteLocalRef(data);
}

/*
 * Class:     com_year2018_ndk_jniNative_Imooc
 * Method:    callStaticMethod
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_jniNative_Imooc_callStaticMethod__JLjava_lang_String_2
        (JNIEnv *env, jclass jClsImooc, jlong ji, jstring jstr){
    if(jClsImooc==NULL){
        return;
    }
    jmethodID mtdStaticMethod = env->GetStaticMethodID(jClsImooc,"staticMethod","(Ljava/lang/String;)V");
    if(mtdStaticMethod==NULL){
        return;
    }
//    jboolean isCopy = JNI_FALSE;
//    LOGD(env->GetStringUTFChars(l,&isCopy));
}

/*
 * Class:     com_year2018_ndk_jniNative_Imooc
 * Method:    callInstanceMethod
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_jniNative_Imooc_callInstanceMethod__I
        (JNIEnv *env, jobject jobject1, jint jint1){

}

/*
 * Class:     com_year2018_ndk_jniNative_Imooc
 * Method:    callInstanceMethod
 * Signature: (Ljava/lang/String;J)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_jniNative_Imooc_callInstanceMethod__Ljava_lang_String_2J
        (JNIEnv *env, jobject jobject1, jstring jstring1, jlong jlong1){

}
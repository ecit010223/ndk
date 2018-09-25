/*
 *  Created by zyh on 2018/9/19.
 */

extern "C"{
#include "../transcode/avilib.h"
}

#include "Common.h"
#include "com_year2018_ndk_activity_graphic_AbstractPlayerActivity.h"


/*
 * Class:     com_year2018_ndk_activity_graphic_AbstractPlayerActivity
 * Method:    open
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_year2018_ndk_activity_graphic_AbstractPlayerActivity_open
        (JNIEnv* env, jclass clazz, jstring fileName){
    avi_t* avi = 0;

    // 获取文件名字赋给C的一个字符串变量
    const char* cFileName = env->GetStringUTFChars(fileName, 0);
    if (0 == cFileName){
        goto exit;
    }

    // 打开avi文件
    avi = AVI_open_input_file(cFileName, 1);

    // 释放文件名字
    env->ReleaseStringUTFChars(fileName, cFileName);

    // 如果avi文件不能打开则抛出一个异常
    if (0 == avi){
        ThrowException(env, "java/io/IOException", AVI_strerror());
    }
    exit:
    return (jlong) avi;
}

/*
 * Class:     com_year2018_ndk_activity_graphic_AbstractPlayerActivity
 * Method:    getWidth
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_year2018_ndk_activity_graphic_AbstractPlayerActivity_getWidth
        (JNIEnv* env, jclass clazz, jlong avi){
    return AVI_video_width((avi_t*) avi);
}

/*
 * Class:     com_year2018_ndk_activity_graphic_AbstractPlayerActivity
 * Method:    getHeight
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_year2018_ndk_activity_graphic_AbstractPlayerActivity_getHeight
        (JNIEnv* env, jclass clazz, jlong avi){
    return AVI_video_height((avi_t*) avi);
}

/*
 * Class:     com_year2018_ndk_activity_graphic_AbstractPlayerActivity
 * Method:    getFrameRate
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_year2018_ndk_activity_graphic_AbstractPlayerActivity_getFrameRate
        (JNIEnv* env, jclass clazz, jlong avi){
    return AVI_frame_rate((avi_t*) avi);
}

/*
 * Class:     com_year2018_ndk_activity_graphic_AbstractPlayerActivity
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_activity_graphic_AbstractPlayerActivity_close
        (JNIEnv* env, jclass clazz, jlong avi){
    AVI_close((avi_t*) avi);
}
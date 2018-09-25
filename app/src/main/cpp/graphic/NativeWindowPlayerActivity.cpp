/*
 *  Created by zyh on 2018/9/20.
 */
extern "C"{
#include "../transcode/avilib.h"
}

#include <android/native_window_jni.h>
#include <android/native_window.h>

#include "Common.h"
#include "com_year2018_ndk_activity_graphic_NativeWindowPlayerActivity.h"

/*
 * Class:     com_year2018_ndk_activity_graphic_NativeWindowPlayerActivity
 * Method:    init
 * Signature: (JLandroid/view/Surface;)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_activity_graphic_NativeWindowPlayerActivity_init
        (JNIEnv* env, jclass clazz, jlong avi, jobject surface){

}

/*
 * Class:     com_year2018_ndk_activity_graphic_NativeWindowPlayerActivity
 * Method:    render
 * Signature: (JLandroid/view/Surface;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_year2018_ndk_activity_graphic_NativeWindowPlayerActivity_render
        (JNIEnv* env, jclass clazz, jlong avi, jobject ){

}

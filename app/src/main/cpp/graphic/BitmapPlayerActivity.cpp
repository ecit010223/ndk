/*
 *  Created by zyh on 2018/9/20.
 */
extern "C"{
#include "../transcode/avilib.h"
}

#include <android/bitmap.h>

#include "Common.h"
#include "com_year2018_ndk_activity_graphic_BitmapPlayerActivity.h"

/*
 * Class:     com_year2018_ndk_activity_graphic_BitmapPlayerActivity
 * Method:    render
 * Signature: (JLandroid/graphics/Bitmap;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_year2018_ndk_activity_graphic_BitmapPlayerActivity_render
        (JNIEnv* env, jclass clazz, jlong avi, jobject bitmap){
    jboolean isFrameRead = JNI_FALSE;

    char* frameBuffer = 0;
    long frameSize = 0;
    int keyFrame = 0;

    // 锁定bitmap并得到raw byte
    if (0 > AndroidBitmap_lockPixels(env, bitmap, (void**) &frameBuffer)){
        ThrowException(env, "java/io/IOException", "Unable to lock pixels.");
        goto exit;
    }

    // 将avi帧bite读到bitmap中
    frameSize = AVI_read_frame((avi_t*) avi, frameBuffer, &keyFrame);

    // 解锁bitmap
    if (0 > AndroidBitmap_unlockPixels(env, bitmap)){
        ThrowException(env, "java/io/IOException", "Unable to unlock pixels.");
        goto exit;
    }

    // 检查帧是否成功读取
    if (0 < frameSize){
        isFrameRead = JNI_TRUE;
    }

    exit:
    return isFrameRead;
}

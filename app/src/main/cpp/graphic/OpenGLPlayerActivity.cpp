/*
 *  Created by zyh on 2018/9/20.
 */
extern "C"{
#include "../transcode/avilib.h"
}

#include <GLES/gl.h>
#include <GLES/glext.h>

#include <malloc.h>

#include "Common.h"
#include "com_year2018_ndk_activity_graphic_OpenGLPlayerActivity.h"


struct Instance {
    char* buffer;
    GLuint texture;

    Instance():
            buffer(0),
            texture(0) {
    }
};

/*
 * Class:     com_year2018_ndk_activity_graphic_OpenGLPlayerActivity
 * Method:    init
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_year2018_ndk_activity_graphic_OpenGLPlayerActivity_init
        (JNIEnv* env, jclass clazz, jlong avi){
    Instance* instance = 0;

    long frameSize = AVI_frame_size((avi_t*) avi, 0);
    if (0 >= frameSize) {
        ThrowException(env, "java/io/RuntimeException","Unable to get the frame size.");
        goto exit;
    }

    instance = new Instance();
    if (0 == instance){
        ThrowException(env, "java/io/RuntimeException","Unable to allocate instance.");
        goto exit;
    }

    instance->buffer = (char*) malloc(frameSize);
    if (0 == instance->buffer){
        ThrowException(env, "java/io/RuntimeException","Unable to allocate buffer.");
        delete instance;
        instance = 0;
    }

    exit:
    return (jlong) instance;
}

/*
 * Class:     com_year2018_ndk_activity_graphic_OpenGLPlayerActivity
 * Method:    initSurface
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_activity_graphic_OpenGLPlayerActivity_initSurface
        (JNIEnv* env, jclass clazz, jlong inst, jlong avi){
    Instance* instance = (Instance*) inst;
    // 启用纹理
    glEnable(GL_TEXTURE_2D);
    // 生成一个纹理对象
    glGenTextures(1, &instance->texture);
    // 绑定到生成的纹理上
    glBindTexture(GL_TEXTURE_2D, instance->texture);

    int frameWidth = AVI_video_width((avi_t*) avi);
    int frameHeight = AVI_video_height((avi_t*) avi);

    // 剪切纹理矩形
    GLint rect[] = {0, frameHeight, frameWidth, -frameHeight};
    glTexParameteriv(GL_TEXTURE_2D, GL_TEXTURE_CROP_RECT_OES, rect);

    // 填充颜色
    glColor4f(1.0, 1.0, 1.0, 1.0);

    // 生成一个空的纹理
    glTexImage2D(GL_TEXTURE_2D,
                 0,
                 GL_RGB,
                 frameWidth,
                 frameHeight,
                 0,
                 GL_RGB,
                 GL_UNSIGNED_SHORT_5_6_5,
                 0);
}

/*
 * Class:     com_year2018_ndk_activity_graphic_OpenGLPlayerActivity
 * Method:    render
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_year2018_ndk_activity_graphic_OpenGLPlayerActivity_render
        (JNIEnv* env, jclass clazz, jlong inst, jlong avi){
    Instance* instance = (Instance*) inst;
    jboolean isFrameRead = JNI_FALSE;
    int keyFrame = 0;

    // 将AVI帧字节读至bitmap
    long frameSize = AVI_read_frame((avi_t*) avi,
                                    instance->buffer,
                                    &keyFrame);

    // 检查帧是否读了
    if (0 >= frameSize){
        goto exit;
    }

    // 读帧
    isFrameRead = JNI_TRUE;

    // 使用新帧更新纹理
    glTexSubImage2D(GL_TEXTURE_2D,
                    0,
                    0,
                    0,
                    AVI_video_width((avi_t*) avi),
                    AVI_video_height((avi_t*) avi),
                    GL_RGB,
                    GL_UNSIGNED_SHORT_5_6_5,
                    instance->buffer);

    // 绘制纹理（该方向找不到声明，由alex注释）
//    glDrawTexiOES(0, 0, 0,
//                  AVI_video_width((avi_t*) avi),
//                  AVI_video_height((avi_t*) avi));

    exit:
    return isFrameRead;
}

/*
 * Class:     com_year2018_ndk_activity_graphic_OpenGLPlayerActivity
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_year2018_ndk_activity_graphic_OpenGLPlayerActivity_free
        (JNIEnv* env, jclass clazz, jlong inst){
    Instance* instance = (Instance*) inst;

    if (0 != instance) {
        free(instance->buffer);
        delete instance;
    }
}
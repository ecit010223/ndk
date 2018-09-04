/*
 *  Created by zyh on 2018/9/4.
 */
#include <android/log.h>

#ifndef NDK_SIMPLELOG_H
#define NDK_SIMPLELOG_H
#ifdef __cplusplus
extern "C" {
#endif

#define LOG_TAG "alex"
#define LOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#ifdef __cplusplus
}
#endif

#endif //NDK_SIMPLELOG_H

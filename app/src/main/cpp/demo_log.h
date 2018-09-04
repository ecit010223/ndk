/*
 *  Created by zyh on 2018/9/4.
 */
#include <jni.h>

#ifndef NDK_DEMO_LOG_H
#define NDK_DEMO_LOG_H

#ifdef __cplusplus
extern "C" {
#endif

jstring native_log(JNIEnv *env, jclass cls);

#ifdef __cplusplus
}
#endif

#endif //NDK_DEMO_LOG_H

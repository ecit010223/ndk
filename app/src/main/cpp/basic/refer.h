/*
 *  Created by zyh on 2018/9/4.
 */
#include <jni.h>
#include "../simple_log.h"

#ifndef NDK_REFER_H
#define NDK_REFER_H

#ifdef __cplusplus
extern "C" {
#endif

void basicJniStatic(JNIEnv *,jclass);
void basicJni(JNIEnv*,jobject);

#ifdef __cplusplus
}
#endif

#endif //NDK_REFER_H

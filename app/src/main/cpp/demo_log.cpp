/*
 *  Created by zyh on 2018/9/4.
 */
#include "full_log.h"
#include "demo_log.h"

jstring native_log(JNIEnv *env, jclass cls){
    MY_LOG_VERBOSE("The stringFromJNI is called");
    MY_LOG_DEBUG("env=%p thiz=%p", env, cls);
    MY_LOG_ASSERT(0!=env, "JNIEnv cannot be NULL.");
    MY_LOG_INFO("Returning a new string.");

    return env->NewStringUTF("native_log");
}

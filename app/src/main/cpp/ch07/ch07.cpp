#include <jni.h>
#include <stdio.h>
#include <unistd.h>

#include <pthread.h>


/*
 *  Created by zyh on 2018/9/6.
 */

// Native worker thread arguments
struct NativeWorkerArgs {
    jint id;
    jint iterations;
};

// Method ID can be cached
static jmethodID gOnNativeMessage = NULL;

// Java VM interface pointer
static JavaVM* gVm = NULL;

// Global reference to object
static jobject gObj = NULL;

// Mutex instance
static pthread_mutex_t mutex;

void nativeInit (JNIEnv* env,jobject obj)
{
    // Initialize mutex
    if (0 != pthread_mutex_init(&mutex, NULL))
    {
        // Get the exception class
        jclass exceptionClazz = env->FindClass(
                "java/lang/RuntimeException");

        // Throw exception
        env->ThrowNew(exceptionClazz, "Unable to initialize mutex");
        goto exit;
    }

    // If object global reference is not set
    if (NULL == gObj)
    {
        // Create a new global reference for the object
        gObj = env->NewGlobalRef(obj);

        if (NULL == gObj)
        {
            goto exit;
        }
    }

    // If method ID is not cached
    if (NULL == gOnNativeMessage)
    {
        // Get the class from the object
        jclass clazz = env->GetObjectClass(obj);

        // Get the method id for the callback
        gOnNativeMessage = env->GetMethodID(clazz,
                                            "onNativeMessage",
                                            "(Ljava/lang/String;)V");

        // If method could not be found
        if (NULL == gOnNativeMessage)
        {
            // Get the exception class
            jclass exceptionClazz = env->FindClass(
                    "java/lang/RuntimeException");

            // Throw exception
            env->ThrowNew(exceptionClazz, "Unable to find method");
        }
    }

    exit:
    return;
}

void nativeFree (
        JNIEnv* env,
        jobject obj)
{
    // If object global reference is set
    if (NULL != gObj)
    {
        // Delete the global reference
        env->DeleteGlobalRef(gObj);
        gObj = NULL;
    }

    // Destory mutex
    if (0 != pthread_mutex_destroy(&mutex))
    {
        // Get the exception class
        jclass exceptionClazz = env->FindClass(
                "java/lang/RuntimeException");

        // Throw exception
        env->ThrowNew(exceptionClazz, "Unable to destroy mutex");
    }
}

void nativeWorker (JNIEnv* env,jobject obj,jint id,jint iterations)
{
    // Lock mutex
    if (0 != pthread_mutex_lock(&mutex))
    {
        // Get the exception class
        jclass exceptionClazz = env->FindClass(
                "java/lang/RuntimeException");

        // Throw exception
        env->ThrowNew(exceptionClazz, "Unable to lock mutex");
        goto exit;
    }

    // Loop for given number of iterations
    for (jint i = 0; i < iterations; i++)
    {
        // Prepare message
        char message[26];
        sprintf(message, "Worker %d: Iteration %d", id, i);

        // Message from the C string
        jstring messageString = env->NewStringUTF(message);

        // Call the on native message method
        env->CallVoidMethod(obj, gOnNativeMessage, messageString);

        // Check if an exception occurred
        if (NULL != env->ExceptionOccurred())
            break;

        // Sleep for a second
        sleep(1);
    }

    // Unlock mutex
    if (0 != pthread_mutex_unlock(&mutex))
    {
        // Get the exception class
        jclass exceptionClazz = env->FindClass(
                "java/lang/RuntimeException");

        // Throw exception
        env->ThrowNew(exceptionClazz, "Unable to unlock mutex");
    }

    exit:
    return;
}

static void* nativeWorkerThread (void* args)
{
    JNIEnv* env = NULL;

    // Attach current thread to Java virtual machine
    // and obrain JNIEnv interface pointer
    if (0 == gVm->AttachCurrentThread(&env, NULL))
    {
        // Get the native worker thread arguments
        NativeWorkerArgs* nativeWorkerArgs = (NativeWorkerArgs*) args;

        // Run the native worker in thread context
        nativeWorker(env,gObj,nativeWorkerArgs->id,nativeWorkerArgs->iterations);

        // Free the native worker thread arguments
        delete nativeWorkerArgs;

        // Detach current thread from Java virtual machine
        gVm->DetachCurrentThread();
    }

    return (void*) 1;
}

void posixThreads (JNIEnv* env,jobject obj,jint threads,jint iterations)
{
    // Create a POSIX thread for each worker
    for (jint i = 0; i < threads; i++)
    {
        // Thread handle
        pthread_t thread;

        // Native worker thread arguments
        NativeWorkerArgs* nativeWorkerArgs = new NativeWorkerArgs();
        nativeWorkerArgs->id = i;
        nativeWorkerArgs->iterations = iterations;

        // Create a new thread
        int result = pthread_create(
                &thread,
                NULL,
                nativeWorkerThread,
                (void*) nativeWorkerArgs);

        if (0 != result)
        {
            // Get the exception class
            jclass exceptionClazz = env->FindClass(
                    "java/lang/RuntimeException");

            // Throw exception
            env->ThrowNew(exceptionClazz, "Unable to create thread");
        }
    }
}

/**
 * JNINativeMethod由三部分组成,可添加多组对应:
 * (1)Java中的函数名;
 * (2)函数签名,格式为（输入参数类型）返回值类型;
 *    ()Ljava/lang/String;
 *    ()：表示无参，
 *    Ljava/lang/String; ：表示返回String，在对象类名（包括包名，‘/’间隔）前面加L，分号结尾
 * (3)是函数指针，指向C函数
 */
static JNINativeMethod jniMethods[] = {
        {
                "nativeInit",    //java层的函数名
                "()V",     //java层函数的参数类型与返回值类型标识
                (void *) nativeInit       //native层对应的函数
        },
        {
                "nativeFree",
                "()V",
                (void *)nativeFree
        },
        {
                "nativeWorker",
                "(II)V",
                (void *)nativeWorker
        },
        {
                "posixThreads",
                "(II)V",
                (void *)posixThreads
        }
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm,void *reserved){
    // Cache the JavaVM interface pointer
    gVm = jvm;

    JNIEnv *env = NULL;
    jint result = JNI_FALSE;

    //获取env指针
    if (jvm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }
    if (env == NULL) {
        return result;
    }
    //获取类引用，写类的全路径（包名+类名）。
    jclass clazz = env->FindClass("com/year2018/ndk/activity/ThreadActivity");
    if (clazz == NULL) {
        return result;
    }
    //注册方法
    if (env->RegisterNatives(clazz, jniMethods, sizeof(jniMethods) / sizeof(jniMethods[0])) < 0) {
        return result;
    }
    //成功
    result = JNI_VERSION_1_6;
    return result;
}


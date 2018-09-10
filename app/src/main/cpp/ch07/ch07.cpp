#include <jni.h>
#include <stdio.h>
#include <unistd.h>

#include <pthread.h>


/*
 *  Created by zyh on 2018/9/6.
 */

// 原生worker线程参数
struct NativeWorkerArgs {
    jint id;
    jint iterations;
};

// 被缓存的方法ID
static jmethodID gOnNativeMessage = NULL;

// Java虚拟机接口指针
static JavaVM* gVm = NULL;

// 对象的全局引用
static jobject gObj = NULL;

// 互斥实例
static pthread_mutex_t mutex;

void nativeInit (JNIEnv* env,jobject obj) {
    // 初始化互斥
    if (0 != pthread_mutex_init(&mutex, NULL)){
        // 获取异常类
        jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");

        // 抛出异常
        env->ThrowNew(exceptionClazz, "Unable to initialize mutex");
        goto exit;
    }

    // 如果回调至UI的Java对象未被设置为全局引用
    if (NULL == gObj){
        // 为对象创建一个新的全局引用
        gObj = env->NewGlobalRef(obj);
        if (NULL == gObj) {
            goto exit;
        }
    }

    // 如果UI回调方法未缓存
    if (NULL == gOnNativeMessage) {
        // Get the class from the object
        jclass clazz = env->GetObjectClass(obj);

        // Get the method id for the callback
        gOnNativeMessage = env->GetMethodID(clazz,"onNativeMessage","(Ljava/lang/String;)V");

        // If method could not be found
        if (NULL == gOnNativeMessage) {
            // Get the exception class
            jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");
            // Throw exception
            env->ThrowNew(exceptionClazz, "Unable to find method");
        }
    }

    exit:
    return;
}

void nativeFree (JNIEnv* env, jobject obj) {
    // 如果对象的全局引用已经设置
    if (NULL != gObj) {
        // 删除全局引用
        env->DeleteGlobalRef(gObj);
        gObj = NULL;
    }

    // 销毁互斥锁
    if (0 != pthread_mutex_destroy(&mutex)) {
        // 获取异常类
        jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");

        // 抛出异常
        env->ThrowNew(exceptionClazz, "Unable to destroy mutex");
    }
}

void nativeWorker (JNIEnv* env,jobject obj,jint id,jint iterations){
    // 锁定互斥锁
    if (0 != pthread_mutex_lock(&mutex)) {
        // 获取异常类
        jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");

        // 抛出异常
        env->ThrowNew(exceptionClazz, "Unable to lock mutex");
        goto exit;
    }

    // Loop for given number of iterations
    for (jint i = 0; i < iterations; i++){
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

    // 解锁互斥锁
    if (0 != pthread_mutex_unlock(&mutex)){
        // 获取异常类
        jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");

        // 抛出异常
        env->ThrowNew(exceptionClazz, "Unable to unlock mutex");
    }

    exit:
    return;
}

/** 原生线程执行函数 **/
static void* nativeWorkerThread (void* args){
    JNIEnv* env = NULL;

    //将当前线程附加到Java虚拟机上，并且获得JNIEnv接口指针
    if (0 == gVm->AttachCurrentThread(&env, NULL)){
        // 获取原生worker线程参数
        NativeWorkerArgs* nativeWorkerArgs = (NativeWorkerArgs*) args;

        // 在线程上下文中运行原生worker
        nativeWorker(env,gObj,nativeWorkerArgs->id,nativeWorkerArgs->iterations);

        // 释放原生worker线程参数
        delete nativeWorkerArgs;

        // 从Java虚拟机中分离当前线程
        gVm->DetachCurrentThread();
    }

    return (void*) 1;
}

/**
 * TODO中内容为后加，添加后UI界面的更新会等到线程执行结束后
 * @param env
 * @param obj
 * @param threads
 * @param iterations
 */
void posixThreads (JNIEnv* env,jobject obj,jint threads,jint iterations){
    //TODO 线程句柄
    pthread_t* handles = new pthread_t[threads];

    // 为每个worker创建一个POSIX线程
    for (jint i = 0; i < threads; i++){
        // 原生worker线程参数
        pthread_t thread;

        // 原生worker线程参数
        NativeWorkerArgs* nativeWorkerArgs = new NativeWorkerArgs();
        nativeWorkerArgs->id = i;
        nativeWorkerArgs->iterations = iterations;

        // 创建新线程
//        int result = pthread_create(&thread,NULL,nativeWorkerThread,(void*) nativeWorkerArgs);
        int result = pthread_create(&handles[i],NULL,nativeWorkerThread,(void*) nativeWorkerArgs);

        if (0 != result){
            // 获取异常类
            jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");

            // 抛出异常
            env->ThrowNew(exceptionClazz, "Unable to create thread");
            //TODO
            goto exit;
        }
    }
    //TODO 等待线程终止
    for(jint i=0;i<threads;i++){
        void* result = NULL;
        //连接每个线程句柄
        if(0!=pthread_join(handles[i],&result)){
            //获取异常类
            jclass exceptionClazz = env->FindClass("java/lang/RuntimeException");
            //抛出异常
            env->ThrowNew(exceptionClazz,"Unable to join thread");
        }else{
            //准备message
            char message[26];
            sprintf(message,"Worker %d returned %d",i,result);
            //来自C字符串的message
            jstring messageString = env->NewStringUTF(message);
            env->CallVoidMethod(obj,gOnNativeMessage,messageString);
            //检查是否产生异常
            if(NULL != env->ExceptionOccurred()){
                goto exit;
            }
        }
    }
    exit:
        return;
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
        //当使用java层的线程运行时取消注释
//        {
//                "nativeWorker",
//                "(II)V",
//                (void *)nativeWorker
//        },
        {
                "posixThreads",
                "(II)V",
                (void *)posixThreads
        }
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm,void *reserved){
    // 缓存Java虚拟机接口指针
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


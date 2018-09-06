/*
 *  Created by zyh on 2018/9/6.
 */
#include <stdlib.h>
#include <stdio.h>
#include "../simple_log.h"

void demo_process(){
    int result;

    /**
     * 可以用system函数向shell传递命令，该函数阻塞了原生代码直到命令执行结束。
     * system命令不为原生应用程序提供接收进程的输出或者给运行的进程发送命令的通信通道，
     * 命令执行结束前原生代码一直在等待。
     * 应包含<stdlib.h>头文件
     */
    result = system("mkdir /data/data/com.year2018.ndk/process.txt");
    if(-1==result||127==result){
        // 执行shell命令失败
    }

    /**
     * popen函数在父进程和子进程之间打开了个双向通道，应包含<stdio.h>标准头文件。
     * popen函数把将要执行的命令以及要求的通信信道类型作为参数，返回一个流指针，
     * 若出现错误，则返回NULL。
     * 默认情况下，popen流是完全缓冲的，需要时可以使用fflush函数刷新缓冲区
     */
    FILE* stream;
    //向ls命令打开一个只读通道
    stream = popen("ls","r");
    if(NULL == stream){
        LOGE("Unable to execute the command.");
    } else{
        char buffer[1024];
        int status;
        //从命令输出中读取每一行
        while(NULL!=fgets(buffer,1024,stream)){
            LOGI("process exited with status %d",status);
        }
    }
    if(0!=pclose(stream)){
        //错误
    }

}


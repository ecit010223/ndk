/*
 *  Created by zyh on 2018/9/6.
 */
#include <stdio.h>
#include "../simple_log.h"

void demo_io(){
    char data[] = {'h','e','l','l','o','\n'};
    size_t writeCount = sizeof(data)/ sizeof(data[0]);
    char c = 'c';
    char buffer[1024];
    size_t readCount = 4;

    //若文件是以r+、w+或a+双模式打开的，在读写转换之前应先用fflush函数刷新缓冲区
    FILE* stream = fopen("/data/data/com.year2018.ndk/io_demo.txt","w+");
    if(NULL == stream){
        //写文件打不开
    }else{
        //向流中写入数据块：从缓冲区data向给定的流stream写count个大小为sizeof(char)的元素
        if(writeCount!=fwrite(data, sizeof(char),writeCount,stream)){
            //向流中写数据时产生错误
        }
        //向流中写字符序列
        if(EOF == fputs("hello\n",stream)){

        }
        //向流中写一个单个字符
        if(c!=fputc(c,stream)){
            //向字符串中写字符时产生错误
        }
        /**
         * 向流中写带格式的数据，返回写入流中的字符个数，错误返回一个负数
         * %d、%i：将整数参数格式化为有符号十进制数
         * %u：将无符号整数格式化为无符号十进制数
         * %o：将无符号整数参数格式化为八进制
         * %x：将无符号整数参数格式化为十六进制
         * %c：将整数参数格式化为单个字符
         * %f：将双精度参数格式化为浮点数
         * %e：将双精度参数格式化为固定格式
         * %s：打印给出的NULL结尾字符数组
         * %p：打印给出的指针作为内存地址
         * %%：写入一个%字符
         */
        if(0>fprintf(stream,"The %s is %d.","number",2)){
            //写入错误
        }
        /**
         * 流I/O积累写入的数据并异步地将其传送至底层文件中，而不是立即将数据写入文件。
         * 类似的，流I/O从文件中以块的方式读取数据而不是逐个字符地读，这就是缓冲。
         * 刷新缓冲区意味着将所有积累的数据传送到底层文件中，在以下情况下刷新会自动进行：
         * (1)应用程序正常终止
         * (2)在行缓冲时写入新行
         * (3)当缓冲区已满
         * (4)当流被关闭
         */
        //手动刷新缓冲区
        if(EOF == fflush(stream)){
            //清空缓冲区产生错误
        }

        char readBuffer[5];
        /**
         * 从流中读取数据块
         * 从给定的流stream中读取readCount个sizeof(char)大小的元素并放入缓冲区buffer中
         */
        if(readCount!=fread(readBuffer, sizeof(char),readCount,stream)){
            //读取失败
        }else{
            //以空结尾
            readBuffer[4] = NULL;
            //输出缓冲区
            LOGD("read:%s",readBuffer);
        }
        /**
         * 从流中读取换行符结尾的字符序列
         * 从给定的流stream中最多读取1023个字符再加上换行符，并将新行的字符内容放入字符数组buffer中
         */
        if(NULL == fgets(buffer,1024,stream)){
            //读取错误
        }else{
            LOGD("read:%s",buffer);
        }
        unsigned char ch;
        int result;
        //从流中读取单个字符
        result = fgetc(stream);
        if(EOF==result){
            //读取错误
        } else{
            ch = (unsigned  char)result;
        }
        char s[5];
        int i;
        /**
         * 从流中读取带格式的数据，成功，则返回读取的项目个数，错误则返回EOF
         */
        if(2!=fscanf(stream,"The %s is %d",s,&i)){
            //错误
        }
        //检查文件结尾
        char bufferEOF[1024];
        while(0==feof(stream)){
            fgets(bufferEOF,1024,stream);
            LOGD("read,%s",bufferEOF);
        }
        /**
         * 搜索位置
         * SEEK_SET：偏移量相对于流的开头
         * SEEK_CUR：偏移量相对于当前位置
         * SEEK_END：偏移量相对于流结尾
         */
        //倒回4个字节
        fseek(stream,-4,SEEK_CUR);
        /**
         * 错误检查
         * 大多数流I/O函数返回EOF来表示错误并报告文件结尾，如果在之前的操作中发生了错误，
         * 则可以用ferror函数进行错误检查，如果给定流的错误标志已被设置为给定流，那么
         * ferror函数会返回一个非零值。
         */
        if(0!=ferror(stream)){
            //前一次请求中产生错误
        }

        //关闭流
        if(0!=fclose(stream)){
            //错误有可能表示由于磁盘空间不足，缓冲的输出不能被写入到流中。
        }
    }
}


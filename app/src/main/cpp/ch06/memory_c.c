/*
 *  Created by zyh on 2018/9/6.
 */
#include <stdlib.h>

//分配16个元素的整形数组
void demo_memory(){
    int* dynamicIntArray = (int*) malloc(sizeof(int)*16);
    // 改变内存大小
    int* newDynamicIntArray = (int*)realloc(dynamicIntArray, sizeof(int)*32);
    if(NULL == newDynamicIntArray){
        //不能分配足够的内存
    }else{
        //通过整形指针使用内存
        *newDynamicIntArray = 0;
        newDynamicIntArray[8] = 8;
        //...
        //释放分配的内存
        free(dynamicIntArray);
        newDynamicIntArray = NULL;
    }
}

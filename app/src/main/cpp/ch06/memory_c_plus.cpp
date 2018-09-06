#include <cstddef>

/*
 *  Created by zyh on 2018/9/6.
 */
void demo_memory_c_plus(){
    int * dynamicInt = new int;
    if (NULL==dynamicInt){
        //不能分配足够的内存
    }else{
        // 使用已经分配的内存
        *dynamicInt = 0;
        // ...
        //释放内存
        delete dynamicInt;
        dynamicInt = 0;
    }

    int* dynamicIntArray = new int[16];
    if(NULL == dynamicIntArray){
        //不能分配足够的内存
    }else{
        //使用已经分配的内存
        dynamicIntArray[8]= 8;
        // ...
        //释放内存
        delete [] dynamicIntArray;
        dynamicIntArray = 0;
    }
}


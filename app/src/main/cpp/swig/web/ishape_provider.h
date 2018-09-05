/*
 *  Created by zyh on 2018/9/5.
 */

#include <stdio.h>
#include <string>

#ifndef NDK_ISHAPE_PROVIDER_H
#define NDK_ISHAPE_PROVIDER_H

//这是一个读写SHAPE数据的接口类;文件名小写测
class IShapeProvider
{
public:
    //打开一个文件或者一个数据库
    virtual void openFile(const std::string& file_name) = 0;

    virtual void extent(int* x,int* y,int* with,int* height)=0;

//获取类的信息,由于toString方法在Java中的方法冲突，所以我们需要给该函数改一个名字
#ifdef SWIG
    %rename (toJavaString)  toString;//()里面是修改之后的函数名字，toString是在C++的函数名字;
#endif
    virtual std::string toString()=0;
};

#endif //NDK_ISHAPE_PROVIDER_H

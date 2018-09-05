/*
 *  Created by zyh on 2018/9/5.
 */

#ifndef NDK_CLASS_FACTORY_H
#define NDK_CLASS_FACTORY_H

#include "ishape_provider.h"
#include "shape_provider.h"

class ClassFactory
{
public:
    //打开一个文件或者一个数据库
    virtual IShapeProvider* newInstance(const std::string& class_name)
    {
        //实现在CPP文件中
        if (class_name == "IShapeProvider")
        {
            return new ShapeProvider();
        }
    };
    virtual void releaseClass(IShapeProvider* shape_provider)
    {
        delete shape_provider;
    }

};

#endif //NDK_CLASS_FACTORY_H

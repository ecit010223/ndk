/*
 *  Created by zyh on 2018/9/5.
 */
#ifndef NDK_SHAPEPROVIDER_H
#define NDK_SHAPEPROVIDER_H

#include "ishape_provider.h"
#include "../../simple_log.h"

class ShapeProvider: public IShapeProvider
{
public:
    //打开一个文件或者一个数据库
    virtual void openFile(const std::string& file_name)
    {
        LOGD("extent");
    }

    virtual void extent(int* x,int* y,int* with,int* height){
        LOGD("openFile");
    }

    virtual std::string toString(){
    }

private:
    void initTableInformaction() {}

};

#endif //NDK_SHAPEPROVIDER_H

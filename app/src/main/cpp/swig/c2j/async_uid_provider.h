/*
 *  Created by zyh on 2018/9/5.
 */

#ifndef NDK_ASYNC_UID_PROVIDER_H
#define NDK_ASYNC_UID_PROVIDER_H

#include <unistd.h>

class AsyncUidProvider{
public:
    AsyncUidProvider(){}

    virtual ~ AsyncUidProvider(){}

    void get(){
        onUid(getuid());
    }

    virtual void onUid(uid_t uid){}
};

#endif //NDK_ASYNC_UID_PROVIDER_H

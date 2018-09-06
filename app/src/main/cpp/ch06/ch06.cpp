/*
 *  Created by zyh on 2018/9/6.
 */
#include <sys/system_properties.h>
#include <unistd.h>
#include "../simple_log.h"

void system_property(){
    char value[PROP_VALUE_MAX];

    /**
     * 它将null结尾的属性值复制到所提供的值指针并返回值的大小，复制的总字节数不会超过PROP_VALUE_MAX
     */
    if(0==__system_property_get("ro.product.model",value)){
        // 系统属性未找到或值为空
    } else{
        LOGI("product model:%s",value);
    }

    const prop_info* property;

    /**
     * 获取一个指向系统属性的直接指针。
     * 它通过名称搜索系统属性，如果找到指定属性，就会返回一个指向它的指针；否则返回NULL。
     * 在系统的生命周期内返回的指针一直有效，且它可以缓存以方便日后查询。
     */
    property = __system_property_find("ro.product.model");
    if(NULL == property){
        //系统属性未找到
    } else{
        char name[PROP_NAME_MAX];
        char value[PROP_VALUE_MAX];
        /**
         * 用指向系统属性的指针和另外两个指向返回的系统属性名称和属性值的字符数组指针作为参数。
         * 它将以null结尾的属性值复制到提供的值指针中，并返回值的大小。
         */
        if(0==__system_property_read(property,name,value)){
            LOGI("system_property is empty.");
        }else{
            LOGI("%s:%s",name,value);
        }
    }
}

/**
 * 每一个安装好的应用程序都从10000开始获取自己的用户ID和组ID，较低的ID用于系统服务。
 */
void user(){
    uid_t  uid;
    //获取应用程序的用户ID
    uid = getuid();

    gid_t  gid;
    //获取应用程序的组ID
    gid = getgid();

    //每个安装好的应用程序都获取分配给用户的用户名，用户名以"app_”开头，后接应用程序号。
    char* username;
    //获取应用程序的用户名
    username = getlogin();
}


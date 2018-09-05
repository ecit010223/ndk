%module Plus

%{
    //包含POSIX操作系统API.
    #include <unistd.h>
    #include "plus.h"
    #include "A.h"
%}

//告诉SWIG uid_t.
typedef unsigned int uid_t;

/*
 * 在原生代码中，C/C++函数能够抛出异常或者返回错误代码。SWIG允许开发人员通过使用%exception预
 * 处理指令将C/C++异常和错误转换成Java异常，进而将异常处理代码引入生成的封装代码中。
 * 异常处理代码可以在接口文件中定义。
 * 异常处理代码在实际函数声明前定义。
 */
//getuid函数的异常处理
/**
%exception getuid{
    $action
    if(!result){
        jclass clazz = jenv->FindClass("java/lang/OutOfMemoryError");
        jenv->ThrowNew(clazz,"Out of Memory");
        return $null;
    }
}
**/
/*
 * 由于代码抛出了一个运行库异常，生成的Java代码没有改变。如果抛出一个检查的异常，
 * SWIG可以通过%javaexception预处理指令做出反应并生成相应的java方法
 */
//抛出检查异常
%javaexception("java.lang.IllegalAccessException") getuid{
    $action
    if(!result){
        jclass clazz = jenv->FindClass("java/lang/IllegalAccessException");
        jenv->ThrowNew(clazz,"Illegal Access");
        return $null;
    }
}

//让SWIG包装getuid函数
extern uid_t getuid(void);

//全局变量,SWIG将生成相应的getter和setter方法，以便在Java应用程序中访问全局变量
extern int counter;

/*
 * 常量定义：SWIG将生成一个名为<Module>Constant的Java接口，在这个接口中常量被
 * 定义为 static final变量
 */
//告诉SWIG生成一个编译时常量
%javaconst(1);
//常量定义方式一：define
#define MAX_WIDTH 640

//告诉SWIG生成一个运行库常量，默认为运行库常量，通过运行库在原生代码中调用JNI函数完成常量初始化
%javaconst(0);
//常量定义方式二：%constant
%constant int MAX_HEIGHT = 320;

//启用只读模式
%immutable;
//只读变量，不会为只读变量生成setter方法
extern int readOnly;
//禁用只启动模式
%mutable;
//读-写变量
extern int readWrite;

//匿名枚举，SWIG在<Module>Constant.java接口中为每个枚举生成final静态变量。
//和常量一样，也可以生成运行库枚举，用%javaconst预处理指令可以生成编译时枚举
enum{E_ONE=1,E_TWO=2,E_THREE,E_FOUR};

//命名枚举，SWIG用枚举名定义了一个单独的类，对应的枚举值被展示为final静态成员域
//命名枚举展示给Java的是类型-安全枚举，它允许做类型检查，而且比基于常量的方法更安全
enum Safe{S_ONE = 1, S_TWO = 2, S_THREE, S_FOUR};

//类型-不安全枚举，是前两种方法的组合。枚举类型被封装在它所在的类中，但是枚举值以静态
//final变量的形式展示。可以通过包含enumtypeunsafe.swg文件将命名枚举标记为类型-不安全
//枚举。
%include "enumtypeunsafe.swg"
enum Unsafe{U_ONE=1,U_TWO=2,U_THREE,U_FOUR};

//java枚举
%include "enums.swg"
enum JEnum{J_ONE=1,J_TWO=2,J_THREE,J_FOUR};

//结构体
//它们和成员变量的getters方法及setters方法一起封装为Java类。
//SWIG也支持指针，如下实例SWIG存储的是Java类中实际的C语言结构体实例的C指针。
//SWIG用long数据类型来存储指针，它通过使用finalize方法管理C语言组件的生成期，
//该C语言组件的生存期与相关Java类的生成期是一致的。
struct Point{
    int x;
    int y;
};

//带默认参数的函数
void defaultValueFunc(int a=1,int b=2,int c=3);

//重载函数
void overloadFunc(double d);
void overloadFunc(int i);

//类A（综合本书与web得到的方式）
%include "A.h"
//指定模块名 directors="1" 代表可以对C++的类在JAVA中继承
%module(directors="1") Shape
%{
    #include "ishape_provider.h"   //在后面生成的Shape.cpp代码中包含的头文件
    #include "class_factory.h"
%}
%include "ishape_provider.h"   //将该头文件所有的类、宏定义、以及全局变量包装到JAVA中,生成对应的类
%include "class_factory.h"
//添加系统的一些文件，处理一些常用的基本类型，具体可以参考SWIG的帮助文档
%include stdint.i
%include carrays.i
%include windows.i
%include typemaps.i
#ifdef SWIGJAVA   //swig到java的swig预定义宏
%include <enums.swg>
%rename (toDString) toString;
#endif
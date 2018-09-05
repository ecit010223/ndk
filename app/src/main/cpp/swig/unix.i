// 模块名 Unix
%module Unix

%{
// 包含POSIX操作系统API.
#include <unistd.h>
%}

// 告诉SWIG uid_t.
typedef unsigned int uid_t;

// 让SWIG包装getuid函数
extern uid_t getuid(void);
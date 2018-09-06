%module(directors = 1) C2J
//启用AsyncUidProvider的directors(注意：必须将%feature放在最前面，才能启用C调Java)
%feature("director")AsyncUidProvider;

%{
    #include "../../simple_log.h"
    #include "async_uid_provider.h"
%}

//告诉SWIG uid_t.
typedef unsigned int uid_t;

%include "async_uid_provider.h"



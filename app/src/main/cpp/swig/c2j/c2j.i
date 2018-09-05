%module(directors = 1) C2J

//告诉SWIG uid_t.
typedef unsigned int uid_t;

%include "async_uid_provider.h"
//启用AsyncUidProvider的directors
%feature("director")AsyncUidProvider;
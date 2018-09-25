package com.year2018.ndk.util;

import android.util.Log;

import com.year2018.ndk.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: zyh
 * Date: 2018/9/20 11:23
 */
public class TLogger {
    private static LogToFile sLogToFile;

    private TLogger(){}

    static {
        if(Constant.Log.DEVELOPMENT){
            sLogToFile = new LogToFile();
        }
    }

    public static final void v(final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.v(Constant.Log.TAG,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.VERBOSE,Constant.Log.TAG,msg);
            }
        }
    }

    public static final void d(final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.d(Constant.Log.TAG,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.DEBUG,Constant.Log.TAG,msg);
            }
        }
    }

    public static final void i(final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.i(Constant.Log.TAG,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.INFO,Constant.Log.TAG,msg);
            }
        }
    }

    public static final void w(final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.w(Constant.Log.TAG,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.WARN,Constant.Log.TAG,msg);
            }
        }
    }

    public static final void e(final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.e(Constant.Log.TAG,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.ERROR,Constant.Log.TAG,msg);
            }
        }
    }

    public static final void v(final String tag,final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.v(tag,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.VERBOSE,tag,msg);
            }
        }
    }

    public static final void d(final String tag,final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.d(tag,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.DEBUG,tag,msg);
            }
        }
    }

    public static final void i(final String tag,final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.i(tag,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.INFO,tag,msg);
            }
        }
    }

    public static final void w(final String tag, final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.w(tag,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.WARN,tag,msg);
            }
        }
    }

    public static final void e(final String tag,final String msg){
        if(Constant.Log.DEVELOPMENT){
            Log.e(tag,msg);
            if(sLogToFile!=null&&Constant.Log.LOG_TO_FILE){
                sLogToFile.writeLogToFile(Log.ERROR,tag,msg);
            }
        }
    }
}

class LogToFile{
    public void writeLogToFile(int priority,String tag,String message){
        Date date = new Date();
        SimpleDateFormat msgDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
        String msgDatetime = msgDateTimeFormat.format(date);
        SimpleDateFormat filenameDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String filenameDate = filenameDateFormat.format(date);
        FileManager.getInstance().writeStringToExternalStorage(Constant.Log.FILENAME+filenameDate+".txt",msgDatetime+" "+
                strPriority[priority]+" "+tag+" "+message+"\n");
    }

    private static final String strPriority[];

    static {
        strPriority = new String[8];
        strPriority[0] = "";
        strPriority[1] = "";
        strPriority[2] = "VERBOSE";
        strPriority[3] = "DEBUG";
        strPriority[4] = "INFO";
        strPriority[5] = "WARN";
        strPriority[6] = "ERROR";
        strPriority[7] = "ASSERT";
    }
}

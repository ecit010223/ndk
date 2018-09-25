package com.year2018.ndk;

/**
 * Author: zyh
 * Date: 2018/9/4 9:19
 */
public interface Constant {
    int REQUEST_PERMISSION_CODE = 1809;
    interface Log {
        String TAG = "alex";
        boolean DEVELOPMENT = true;
        boolean LOG_TO_FILE = false;
        String FILENAME = "log_ndk_";
    }
}

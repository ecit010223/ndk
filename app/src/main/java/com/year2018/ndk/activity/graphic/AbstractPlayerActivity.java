package com.year2018.ndk.activity.graphic;

import android.app.Activity;
import android.app.AlertDialog;

import com.year2018.ndk.R;

import java.io.IOException;

/**
 * Author: zyh
 * Date: 2018/9/19 9:07
 */
public abstract class AbstractPlayerActivity extends Activity {
    /** AVI文件名字的extra */
    public static final String EXTRA_FILE_NAME ="com.year2018.ndk.EXTRA_FILE_NAME";
    /** AVI视频文件描述符 */
    protected long avi = 0;

    @Override
    protected void onStart() {
        super.onStart();
        // 打开AVI文件
        try {
            avi = open(getFileName());
        } catch (IOException e) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error_alert_title)
                    .setMessage(e.getMessage())
                    .show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 如果AVI视频是打开的
        if (0 != avi) {
            // 关闭文件描述符
            close(avi);
            avi = 0;
        }
    }

    /**
     * 获取AVI视频文件的名字
     * @return 文件名
     */
    protected String getFileName() {
        return getIntent().getExtras().getString(EXTRA_FILE_NAME);
    }

    /**
     * 打开指定的AVI文件并且返回一个文件描述符
     * @param fileName file name.
     * @return file descriptor.
     * @throws IOException
     */
    protected native static long open(String fileName) throws IOException;

    /**
     * 获得视频宽度
     *
     * @param avi file descriptor.
     * @return video width.
     */
    protected native static int getWidth(long avi);

    /**
     * 获得视频高度
     *
     * @param avi file descriptor.
     * @return video height.
     */
    protected native static int getHeight(long avi);

    /**
     * 获得帧速
     * @param avi file descriptor.
     * @return frame rate.
     */
    protected native static double getFrameRate(long avi);

    /**
     * 基于给定的文件描述符关闭指定的AVI文件
     *
     * @param avi file descriptor.
     */
    protected native static void close(long avi);

    static {
        System.loadLibrary("graphic-lib");
    }
}

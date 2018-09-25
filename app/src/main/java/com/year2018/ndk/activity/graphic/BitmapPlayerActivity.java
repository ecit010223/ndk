package com.year2018.ndk.activity.graphic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.year2018.ndk.R;
import com.year2018.ndk.util.TLogger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: zyh
 * Date: 2018/9/19 9:13
 */
public class BitmapPlayerActivity extends AbstractPlayerActivity {
    /** 正在播放 */
    private final AtomicBoolean isPlaying = new AtomicBoolean();
    /** Surface holder. */
    private SurfaceHolder surfaceHolder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_player);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.sv_bitmap_player);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceHolderCallback);
    }

    /**
     * Surface holder监听surface事件的回调
     */
    private final Callback surfaceHolderCallback = new Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            //surface准备好后开始播放
            isPlaying.set(true);

            // 在一个单独的线程中渲染
            new Thread(renderer).start();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // surface销毁后停止播放
            isPlaying.set(false);
        }
    };

    /**
     * 渲染线程通过一个bitmap将AVI文件中的视频帧渲染到surface上
     */
    private final Runnable renderer = new Runnable() {
        public void run() {
            TLogger.i("width:"+getWidth(avi)+",height:"+getHeight(avi));
            // 创建一个新的bitmap来保存所有的帧
            Bitmap bitmap = Bitmap.createBitmap(
                    getWidth(avi),
                    getHeight(avi),
                    Bitmap.Config.RGB_565);

            // 使用帧速来计算延迟
            long frameDelay = (long) (1000 / getFrameRate(avi));

            // 播放的时间开始渲染
            while (isPlaying.get()) {
                // 将帧渲染至bitmap
                render(avi, bitmap);
                // 锁定canvas
                Canvas canvas = surfaceHolder.lockCanvas();
                // 将bitmap绘制至canvas
                canvas.drawBitmap(bitmap, 0, 0, null);
                // canvas准备显示
                surfaceHolder.unlockCanvasAndPost(canvas);
                // 等待下一帧
                try {
                    Thread.sleep(frameDelay);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    };

    /**
     * 从avi文件描述符输出到指定Bitmap来渲染帧
     *
     * @param avi file descriptor.
     * @param bitmap bitmap instance.
     * @return true if there are more frames, false otherwise.
     */
    private native static boolean render(long avi, Bitmap bitmap);
}

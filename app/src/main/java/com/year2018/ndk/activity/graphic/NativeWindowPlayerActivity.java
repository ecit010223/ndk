package com.year2018.ndk.activity.graphic;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.year2018.ndk.R;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: zyh
 * Date: 2018/9/20 16:54
 * 使用原生容器的avi播放器
 */
public class NativeWindowPlayerActivity extends AbstractPlayerActivity {
    /** 正在播放 */
    private final AtomicBoolean isPlaying = new AtomicBoolean();

    /** Surface存储器 */
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
    private final SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // surface准备好后开始播放
            isPlaying.set(true);

            // 在一个单独的线程中启动渲染器
            new Thread(renderer).start();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // surface销毁时停止播放
            isPlaying.set(false);
        }
    };

    /**
     * 渲染线程通过一个bitmap将avi文件中的视频帧渲染到surface上
     */
    private final Runnable renderer = new Runnable() {
        public void run() {
            // 获得surface实例
            Surface surface = surfaceHolder.getSurface();

            // 初始化原生window
            init(avi, surface);

            // 使用帧速计算延迟
            long frameDelay = (long) (1000 / getFrameRate(avi));

            // 播放时开始渲染
            while (isPlaying.get()) {
                // 将帧渲染至surface
                render(avi, surface);

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
     * 初始化原生window
     * @param avi file descriptor.
     * @param surface surface instance.
     */
    private native static void init(long avi, Surface surface);

    /**
     * 将给定avi文件的帧渲染到给定的surface上
     * @param avi file descriptor.
     * @param surface surface instance.
     * @return true if there are more frames, false otherwise.
     */
    private native static boolean render(long avi, Surface surface);
}

package com.year2018.ndk.activity.graphic;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.year2018.ndk.R;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.microedition.khronos.opengles.GL10;

/**
 * Author: zyh
 * Date: 2018/9/20 15:10
 */
public class OpenGLPlayerActivity extends AbstractPlayerActivity {
    /** 正在播放 */
    private final AtomicBoolean isPlaying = new AtomicBoolean();
    /** 原生渲染器 */
    private long instance;
    private GLSurfaceView glSurfaceView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gl_player);

        glSurfaceView = (GLSurfaceView) findViewById(R.id.glsv_openGL_player);
        // 设置渲染器
        glSurfaceView.setRenderer(renderer);
        // 请求时渲染帧
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    protected void onStart() {
        super.onStart();
        // 初始化原生渲染器
        instance = init(avi);
    }

    protected void onResume() {
        super.onResume();
        //当activity resumed时必须通知GL surface视图
        glSurfaceView.onResume();
    }

    protected void onPause() {
        super.onPause();
        //当activity paused时必须通知GL surface view
        glSurfaceView.onPause();
    }

    protected void onStop() {
        super.onStop();
        //释放原生渲染器
        free(instance);
        instance = 0;
    }

    //根据帧速请求渲染
    private final Runnable player = new Runnable() {
        public void run() {
            // 使用帧速计算延迟
            long frameDelay = (long) (1000 / getFrameRate(avi));
            // 播放时开始渲染
            while (isPlaying.get()) {
                // 请求渲染
                glSurfaceView.requestRender();
                // 等待下一帧
                try {
                    Thread.sleep(frameDelay);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    };

    private final GLSurfaceView.Renderer renderer = new GLSurfaceView.Renderer() {
        @Override
        public void onDrawFrame(GL10 gl) {
            // 渲染下一帧
            if (!render(instance, avi)) {
                isPlaying.set(false);
            }
        }

        @Override
        public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
            // 初始化OpenGL surface
            initSurface(instance, avi);
            //surface准备好后开始播放
            isPlaying.set(true);
            //启动播放器
            new Thread(player).start();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }
    };

    /**
     * 初始化原生渲染器
     * @param avi file descriptor.
     * @return native instance.
     */
    private native static long init(long avi);

    /**
     * 初始化OpenGL surface
     * @param instance native instance.
     */
    private native static void initSurface(long instance, long avi);

    /**
     * 用给定文件进行帧渲染
     * @param instance native instance.
     * @param avi file descriptor.
     * @return true if there are more frames, false otherwise.
     */
    private native static boolean render(long instance, long avi);

    /**
     * 释放原生渲染器
     * @param instance native instance.
     */
    private native static void free(long instance);
}

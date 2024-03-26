package com.example.opencvapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLSurfaceView extends GLSurfaceView {

    private static final String TAG = "MyGLSurfaceView";
    private TriangleRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // 创建OpenGL ES 2.0上下文
        setEGLContextClientVersion(2);

        // 设置渲染器
        mRenderer = new TriangleRenderer();
        setRenderer(new MyRenderer());
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    public MyGLSurfaceView(Context context, AttributeSet set) {
        super(context,null);
        // 创建OpenGL ES 2.0上下文
        setEGLContextClientVersion(2);

        // 设置渲染器
        mRenderer = new TriangleRenderer();
        setRenderer(new MyRenderer());


    }

    private class MyRenderer implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 在OpenGL ES环境创建时调用
            int error = GLES20.glGetError();
            if (error != GLES20.GL_NO_ERROR) {
                Log.e(TAG, "OpenGL error: " + error);
            }
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 清除颜色缓冲区为黑色
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // 在surface尺寸变化时调用
            GLES20.glViewport(0, 0, width, height); // 设置视口大小为屏幕大小
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            // 绘制一帧图像时调用
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT); // 清除颜色缓冲区
            mRenderer.draw(); // 绘制三角形
        }
    }
}

package com.example.opencvapp.opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**

 * @Author: chen

 * @datetime: 2024/3/26

 * @desc:

 */
class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var triangle: Triangle

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        // 我们的例子中在Triangle构造函数中就操作了GLES20，所以一定要在onSurfaceCreated中再去创建Triangle对象
        triangle = Triangle()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        // 绘制三角形
        triangle.draw()
    }
}


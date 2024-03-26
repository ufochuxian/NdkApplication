package com.example.opencvapp.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class MyGLSurfaceView(context: Context?, attrs: AttributeSet?) : GLSurfaceView(context, attrs) {
    private val renderer: MyGLRenderer

    init {
        // 创建一个OpenGL ES 2.0上下文
        setEGLContextClientVersion(2)
        renderer = MyGLRenderer()
        // GLSurfaceView关联Render
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}

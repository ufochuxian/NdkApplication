package com.example.opencvapp;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TriangleRenderer {

    private static final String TAG = "TriangleRenderer";


    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "void main() {" +
                    "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
                    "}";

    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private static final int COORDS_PER_VERTEX = 3;
    private static float triangleCoords[] = {
        0.0f,  0.622008459f, 0.0f,   // top
       -0.5f, -0.311004243f, 0.0f,   // bottom left
        0.5f, -0.311004243f, 0.0f    // bottom right
    };

    private static float lineCoords[] = {
            -0.5f, 0.5f, 0.0f,  // top left
            0.5f, -0.5f, 0.0f   // bottom right
    };

    // 设置颜色(分别代表red, green, blue and alpha)
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public TriangleRenderer() {
        // 为顶点坐标数据分配本地内存，存储顶点坐标数据
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        // 编译着色器代码
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                                       vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                                         fragmentShaderCode);

        // 创建空的OpenGL ES程序，并将着色器连接到它
        mProgram = GLES20.glCreateProgram();             // 创建空的OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // 添加顶点着色器到Program
        GLES20.glAttachShader(mProgram, fragmentShader); // 添加片段着色器到Program
        GLES20.glLinkProgram(mProgram);                  // 创建可执行的OpenGL ES program

        // 释放着色器资源
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader);
    }

    public void draw() {
        // 将程序添加到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);

        // 获取指向vertex shader的成员vPosition的handle
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 启用一个指向三角形顶点数组的handle(允许操作顶点对象position)
        GLES20.glEnableVertexAttribArray(positionHandle);

        // 准备三角形坐标数据
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 禁用指向三角形顶点数组的handle
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    private static int loadShader(int type, String shaderCode) {
        // 创建一个顶点着色器类型(GLES20.GL_VERTEX_SHADER) 或者片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // 将源码添加到着色器并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        // 检查着色器编译是否成功
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            // 编译失败，打印编译日志
            Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
            // 删除着色器对象
            GLES20.glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

}

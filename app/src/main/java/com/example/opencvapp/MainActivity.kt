package com.example.opencvapp

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    private final val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.goToBlur).setOnClickListener {
            this.goToBlurActivity()
        }

        this.checkoutOpenGl()

    }

    private fun goToBlurActivity() {
        val blurActivityIntent =  Intent(this,BlurActivity::class.java)
        this.startActivity(blurActivityIntent)
    }

    private fun checkoutOpenGl() {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        if (configurationInfo.reqGlEsVersion >= 0x20000) {
            // 设备支持OpenGL ES 2.0
            Log.i(TAG,"支持opengl2.0")
        } else {
            // 设备不支持OpenGL ES 2.0
            Log.i(TAG,"不支持opengl2.0")
        }
    }
}
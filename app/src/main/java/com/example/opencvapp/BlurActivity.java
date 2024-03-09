package com.example.opencvapp;

/**
 * @Author: chen
 * @datetime: 2024/3/9
 * @desc:
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;

public class BlurActivity extends ComponentActivity {

    private static final String TAG = "BlurActivity";
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        // 加载图片
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.su7); // 从资源或其他方式加载原始图片

//        // 设置模糊半径
        int radius = 25;

        new Thread(() -> {
            Log.i(TAG, "开始高斯模糊算法");
            final Bitmap blurredBitmap = GaussianBlur.applyGaussianBlur(originalBitmap, radius);
            Log.i(TAG, blurredBitmap.toString());
            Log.i(TAG, "结束高斯模糊算法");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // 将模糊后的图片显示在 ImageView 中
                    ImageView imageView = findViewById(R.id.blurimg);
                    imageView.setImageBitmap(blurredBitmap);
                }
            });
        }).start();
    }
}


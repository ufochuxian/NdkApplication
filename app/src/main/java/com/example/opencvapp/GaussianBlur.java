package com.example.opencvapp;

/**
 * @Author: chen
 * @datetime: 2024/3/9
 * @desc:
 */

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * 1. 自定义高斯模糊算法的卷积核
 * 2. 设定卷积半径
 * 3. 使用这个卷积核集合卷积半径，和原图片的像素，进行卷积算法（比如加和平均算法）
 * 4. 将卷积之后的像素的图像，显示到图像上
 */
public class GaussianBlur {

    // 高斯模糊算法
    public static Bitmap applyGaussianBlur(Bitmap originalBitmap, int radius) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        int[] pixels = new int[width * height];
        originalBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        // 第一次水平模糊
        int[] blurredPixels = horizontalBlur(pixels, width, height, radius);

        // 第二次垂直模糊
        blurredPixels = verticalBlur(blurredPixels, width, height, radius);

        Bitmap blurredBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        blurredBitmap.setPixels(blurredPixels, 0, width, 0, 0, width, height);

        return blurredBitmap;
    }

    // 水平方向模糊
    private static int[] horizontalBlur(int[] pixels, int width, int height, int radius) {
        int[] blurredPixels = new int[width * height];
        float[] kernel = createGaussianKernel(radius);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = 0, green = 0, blue = 0;
                float weightSum = 0;

                for (int i = -radius; i <= radius; i++) {
                    int index = x + i;
                    if (index >= 0 && index < width) {
                        int color = pixels[y * width + index];
                        float weight = kernel[i + radius];
                        red += Color.red(color) * weight;
                        green += Color.green(color) * weight;
                        blue += Color.blue(color) * weight;
                        weightSum += weight;
                    }
                }

                int index = y * width + x;
                blurredPixels[index] = Color.rgb((int) (red / weightSum), (int) (green / weightSum), (int) (blue / weightSum));
            }
        }

        return blurredPixels;
    }

    // 垂直方向模糊
    private static int[] verticalBlur(int[] pixels, int width, int height, int radius) {
        int[] blurredPixels = new int[width * height];
        float[] kernel = createGaussianKernel(radius);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = 0, green = 0, blue = 0;
                float weightSum = 0;

                for (int i = -radius; i <= radius; i++) {
                    int index = y + i;
                    if (index >= 0 && index < height) {
                        int color = pixels[index * width + x];
                        float weight = kernel[i + radius];
                        red += Color.red(color) * weight;
                        green += Color.green(color) * weight;
                        blue += Color.blue(color) * weight;
                        weightSum += weight;
                    }
                }

                int index = y * width + x;
                blurredPixels[index] = Color.rgb((int) (red / weightSum), (int) (green / weightSum), (int) (blue / weightSum));
            }
        }

        return blurredPixels;
    }

    // 创建高斯核
    private static float[] createGaussianKernel(int radius) {
        float[] kernel = new float[radius * 2 + 1];
        float sigma = radius / 3.0f;
        float sigmaSquared = 2 * sigma * sigma;
        float sqrtTwoPiSigma = (float) Math.sqrt(2 * Math.PI * sigma);
        float sum = 0;

        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            kernel[index] = (float) (Math.exp(-distance / sigmaSquared) / sqrtTwoPiSigma);
            sum += kernel[index];
        }

        // 归一化
        for (int i = 0; i < kernel.length; i++) {
            kernel[i] /= sum;
        }

        return kernel;
    }
}


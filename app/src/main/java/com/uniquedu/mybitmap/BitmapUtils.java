package com.uniquedu.mybitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ZhongHang on 2016/4/25.
 */
public class BitmapUtils {
    public static void zipImage(String from, String savePath) {
        //读取Bitmap的配置文件
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为只读取Bitmap的宽高，不读取点的信息
        options.inJustDecodeBounds = true;
        //BitmapFactory读取图片的配置信息
        BitmapFactory.decodeFile(from, options);
        //设置图片的压缩值
        options.inSampleSize = computeSampleSize(options, -1, 480 * 800);
        options.inJustDecodeBounds = false;
        //读取到Bitmap，读取点信息
        Bitmap bitmap = BitmapFactory.decodeFile(from, options);
        try {
            //设置输出流
            FileOutputStream fos = new FileOutputStream(savePath);
            //将Bitmap以JPG的格式按照90%输出到文件中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置回收
        bitmap.recycle();
        //赋值为空，取消内存占用时的引用，易被回收
        bitmap = null;
        //手动调用系统内存回收
        System.gc();
    }

    /**
     * @param options        传入的图片的宽高信息
     * @param minSideLength  图片最窄边的长度
     * @param maxNumOfPixels 图片最大像素点数
     * @return 最终的压缩值
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        //得到一个压缩的初始值
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        //最终值
        int roundedSize;
        if (initialSize <= 8) {
            //如果压缩值小于8,最终值等于1
            roundedSize = 1;
            while (roundedSize < initialSize) {
                //如果1小于压缩值着左移1一位，即乘2
                roundedSize <<= 1;
            }
        } else {
            //变成较大的8的倍数
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    /**
     * @param options        传入的图片的宽高信息
     * @param minSideLength  图片最窄边的长度
     * @param maxNumOfPixels 图片最大像素点数
     * @return 初始的压缩值
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;//图片的宽度
        double h = options.outHeight;//图片的高度
        //根据像素点取压缩倍数 为图片的宽高相乘得到图片实际像素点数 除以 压缩后的最大像素点数 得到一个压缩倍数。该值向上取整
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        //根据最窄边长度取压缩倍数 使用图片的宽高中较小值 除以 压缩后最窄边值 向下取整
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}

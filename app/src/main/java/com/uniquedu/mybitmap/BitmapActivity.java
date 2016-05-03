package com.uniquedu.mybitmap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * 简单介绍Bitmap的使用
 */
public class BitmapActivity extends AppCompatActivity implements View.OnClickListener {

    private android.widget.ImageView imageviewshow;
    private android.widget.Button buttonshow;
    private android.widget.Button buttoncompress;
    private android.widget.TextView textviewmemory;
    private static final int GET_MEMORY = 0x23;
    private ActivityManager manager;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_MEMORY:
                    textviewmemory.setText(msg.obj.toString());
                    break;
            }
        }
    };
    private Button buttonexif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        this.buttonexif = (Button) findViewById(R.id.button_exif);
        buttonexif.setOnClickListener(this);
        manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        this.textviewmemory = (TextView) findViewById(R.id.textview_memory);
        this.buttoncompress = (Button) findViewById(R.id.button_compress);
        this.buttonshow = (Button) findViewById(R.id.button_show);
        this.imageviewshow = (ImageView) findViewById(R.id.imageview_show);
        buttonshow.setOnClickListener(this);
        buttoncompress.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int memoryClass = manager.getMemoryClass();
                    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
                    manager.getMemoryInfo(info);
                    Message msg = handler.obtainMessage();
                    msg.obj = "剩余内存大小为" + info.availMem / 1024 / 1024 + "MB系统目前是否处于低内存运行" + info.lowMemory + "  memoryClass" + memoryClass;
                    msg.what = GET_MEMORY;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show: {
                //图片路径
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/aa.png");
                imageviewshow.setImageBitmap(bitmap);
            }
            break;
            case R.id.button_compress: {
                //压缩图片
                BitmapUtils.zipImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/aa.png", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/bb.png");
            }
            break;
            case R.id.button_exif: {
                getExif();
            }
            break;

        }
    }

    private void getExif() {
        //显示所有的exif信息
        try {
            ExifInterface exif = new ExifInterface(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cc.jpg");
//                    TAG_DATETIME 时间日期
//                    TAG_FLASH 闪光灯
//                    TAG_GPS_LATITUDE 纬度
//                    TAG_GPS_LATITUDE_REF 纬度参考
//                    TAG_GPS_LONGITUDE 经度
//                    TAG_GPS_LONGITUDE_REF 经度参考
//                    TAG_IMAGE_LENGTH 图片长
//                    TAG_IMAGE_WIDTH 图片宽
//                    TAG_MAKE 设备制造商
//                    TAG_MODEL 设备型号
//                    TAG_ORIENTATION 方向
//                    TAG_WHITE_BALANCE 白平衡
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            int degree = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            buttonexif.setText("图片的旋转角度为" + degree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

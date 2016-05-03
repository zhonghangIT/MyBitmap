package com.uniquedu.mybitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CanvasActivity extends AppCompatActivity {

    @InjectView(R.id.imageview_show)
    ImageView imageviewShow;
    @InjectView(R.id.button_color)
    Button buttonColor;
    @InjectView(R.id.button_line)
    Button buttonLine;
    @InjectView(R.id.button_cirle)
    Button buttonCirle;
    @InjectView(R.id.button_bitmap)
    Button buttonBitmap;
    @InjectView(R.id.button_watch)
    Button buttonWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.imageview_show, R.id.button_color, R.id.button_line, R.id.button_cirle, R.id.button_bitmap, R.id.button_watch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_show:
                break;
            case R.id.button_color:
                drawColor();
                break;
            case R.id.button_line:
                drawLine();
                break;
            case R.id.button_cirle:
                drawCircle();
                break;
            case R.id.button_bitmap:
                drawBitmap();
                break;
            case R.id.button_watch:
                drawWatch();

                break;
        }
    }

    private void drawWatch() {
        //绘制一个表盘
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        //开启抗锯齿
        paint.setAntiAlias(true);
        //设置颜色
        paint.setColor(Color.BLACK);
        //设置填充的方式
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔的宽度
        paint.setStrokeWidth(2);
        //绘制表盘的圆
        canvas.drawCircle(imageviewShow.getWidth() / 2, imageviewShow.getHeight() / 2, imageviewShow.getHeight() / 2 - 10, paint);
        //保存当前画布状态，必须和restore配合使用
        canvas.save();
        for (int i = 1; i <= 12; i++) {
            //逆时针旋转画布30度
            canvas.rotate(360 / 12, imageviewShow.getWidth() / 2, imageviewShow.getHeight() / 2);
            //绘制垂直的线
            canvas.drawLine(imageviewShow.getWidth() / 2, 20, imageviewShow.getWidth() / 2, 40, paint);
            //绘制文本
            canvas.drawText("" + i, imageviewShow.getWidth() / 2, 60, paint);
        }
        //恢复旋转前的状态
        canvas.restore();
        imageviewShow.setImageBitmap(bitmap);
    }

    private void drawBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        //绘制图片，默认开始位置为左上角
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.meinv), 0, 0, null);
        imageviewShow.setImageBitmap(bitmap);
    }

    private void drawCircle() {
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        //生成画笔
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.RED);
        //设置画笔的宽度
        paint.setStrokeWidth(20);
        //设置画笔为充满
        paint.setStyle(Paint.Style.FILL);
        //绘制圆，圆心的位置，圆的半径
        canvas.drawCircle(imageviewShow.getWidth() / 2, imageviewShow.getHeight() / 2, 50, paint);
        imageviewShow.setImageBitmap(bitmap);
    }

    private void drawLine() {
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        //生成画笔
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.RED);
        //设置画笔的宽度
        paint.setStrokeWidth(20);
        //绘制直线
        canvas.drawLine(0, 0, imageviewShow.getWidth(), imageviewShow.getHeight(), paint);
        imageviewShow.setImageBitmap(bitmap);
    }

    private void drawColor() {
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.RED);
        imageviewShow.setImageBitmap(bitmap);
    }
}

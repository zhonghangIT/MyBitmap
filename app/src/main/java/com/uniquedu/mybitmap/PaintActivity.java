package com.uniquedu.mybitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PaintActivity extends AppCompatActivity {

    @InjectView(R.id.imageview_show)
    ImageView imageviewShow;
    @InjectView(R.id.button_circle)
    Button buttonCircle;
    @InjectView(R.id.button_anti)
    Button buttonAnti;
    @InjectView(R.id.button_dither)
    Button buttonDither;
    @InjectView(R.id.button_effect)
    Button buttonEffect;
    @InjectView(R.id.button_xmode)
    Button buttonXmode;
    @InjectView(R.id.button_gradient)
    Button buttonGradient;
    private boolean isDither = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.imageview_show, R.id.button_circle, R.id.button_anti, R.id.button_dither, R.id.button_effect, R.id.button_xmode, R.id.button_gradient})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_show:
                break;
            case R.id.button_circle:
                drawCircle(false);
                break;
            case R.id.button_anti:
                drawCircle(true);
                break;
            case R.id.button_dither:
                isDither = !isDither;
                drawBitmap(isDither);
                break;
            case R.id.button_effect:
                //绘制路径效果
                startActivity(new Intent(getApplicationContext(), EffectActivity.class));
                break;
            case R.id.button_xmode:
                break;
            case R.id.button_gradient:
                break;
        }
    }

    private void drawBitmap(boolean isDither) {
        //绘制圆形
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(isDither);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.meinv), 0, 0, paint);
        imageviewShow.setImageBitmap(bitmap);
    }

    private void drawCircle(boolean isAntiAlias) {
        //绘制圆形
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(isAntiAlias);
        canvas.drawCircle(imageviewShow.getWidth() / 2, imageviewShow.getHeight() / 2, 100, paint);
        imageviewShow.setImageBitmap(bitmap);
    }
}

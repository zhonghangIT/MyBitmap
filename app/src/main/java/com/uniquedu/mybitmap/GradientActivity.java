package com.uniquedu.mybitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Android中的贴图Shader，包含渐变
 */
public class GradientActivity extends AppCompatActivity {

    @InjectView(R.id.imageview_show)
    ImageView imageviewShow;
    @InjectView(R.id.radio_clamp)
    RadioButton radioClamp;
    @InjectView(R.id.radio_repeat)
    RadioButton radioRepeat;
    @InjectView(R.id.radio_mirror)
    RadioButton radioMirror;
    @InjectView(R.id.radiogroup)
    RadioGroup radiogroup;
    @InjectView(R.id.button_bitmap)
    Button buttonBitmap;
    @InjectView(R.id.button_compose)
    Button buttonCompose;
    @InjectView(R.id.button_linear)
    Button buttonLinear;
    @InjectView(R.id.button_radia)
    Button buttonRadia;
    @InjectView(R.id.button_sweep)
    Button buttonSweep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);
        ButterKnife.inject(this);
        radioClamp.setChecked(true);
    }

    @OnClick({R.id.button_bitmap, R.id.button_compose, R.id.button_linear, R.id.button_radia, R.id.button_sweep})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_bitmap:
                //图片着色器
                if (radiogroup.getCheckedRadioButtonId() == R.id.radio_clamp) {
                    drawBitmapShader(Shader.TileMode.CLAMP);
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.radio_repeat) {
                    drawBitmapShader(Shader.TileMode.REPEAT);
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.radio_mirror) {
                    drawBitmapShader(Shader.TileMode.MIRROR);
                }

                break;
            case R.id.button_compose:
                    //组合着色器
                break;
            case R.id.button_linear:
                if (radiogroup.getCheckedRadioButtonId() == R.id.radio_clamp) {
                    drawLinear(Shader.TileMode.CLAMP);
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.radio_repeat) {
                    drawLinear(Shader.TileMode.REPEAT);
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.radio_mirror) {
                    drawLinear(Shader.TileMode.MIRROR);
                }
                break;
            case R.id.button_radia:
                if (radiogroup.getCheckedRadioButtonId() == R.id.radio_clamp) {
                    drawRadia(Shader.TileMode.CLAMP);
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.radio_repeat) {
                    drawRadia(Shader.TileMode.REPEAT);
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.radio_mirror) {
                    drawRadia(Shader.TileMode.MIRROR);
                }
                break;
            case R.id.button_sweep:
                drawSweep();
                break;
        }
    }

    /**
     * 绘制图形的贴图
     *
     * @param mode
     */
    private void drawBitmapShader(Shader.TileMode mode) {
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Bitmap bitShader = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        BitmapShader shader = new BitmapShader(bitShader, mode, mode);
        paint.setShader(shader);
        canvas.drawRect(0, 0, imageviewShow.getWidth(), imageviewShow.getHeight(), paint);
        imageviewShow.setImageBitmap(bitmap);
    }

    /**
     * 扇形渐变
     */
    private void drawSweep() {
        SweepGradient sweepGradient = new SweepGradient(imageviewShow.getHeight() / 2, imageviewShow.getHeight() / 2, Color.RED, Color.BLUE);
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setShader(sweepGradient);
        paint.setStrokeWidth(50);
        canvas.drawCircle(imageviewShow.getHeight() / 2, imageviewShow.
                getHeight() / 2, imageviewShow.getHeight() / 2 - 10, paint);
        imageviewShow.setImageBitmap(bitmap);
    }

    /**
     * 圆形渐变
     *
     * @param mode
     */
    private void drawRadia(Shader.TileMode mode) {
        RadialGradient radiaGradient = new RadialGradient(imageviewShow.getHeight() / 2, imageviewShow.getHeight() / 2, 50, Color.RED, Color.BLUE, mode);
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setShader(radiaGradient);
        paint.setStrokeWidth(50);
        canvas.drawCircle(imageviewShow.getHeight() / 2, imageviewShow.
                getHeight() / 2, imageviewShow.getHeight() / 2 - 10, paint);
        imageviewShow.setImageBitmap(bitmap);
    }

    /**
     * 线性渐变
     *
     * @param mode
     */
    private void drawLinear(Shader.TileMode mode) {
        LinearGradient linearGradient = new LinearGradient(0, 0, imageviewShow.getWidth() / 4, 0, Color.RED, Color.BLUE, mode);
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setShader(linearGradient);
        paint.setStrokeWidth(50);
        canvas.drawLine(0, 50, imageviewShow.getWidth(), 50, paint);
        imageviewShow.setImageBitmap(bitmap);
    }
}

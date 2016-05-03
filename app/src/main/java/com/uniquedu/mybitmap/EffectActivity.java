package com.uniquedu.mybitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EffectActivity extends AppCompatActivity {

    @InjectView(R.id.imageview_show)
    ImageView imageviewShow;
    @InjectView(R.id.button_draw)
    Button buttonDraw;
    private float mPhase;
    private PathEffect[] mEffects = new PathEffect[7];
    private int[] mColors;
    private Paint mPaint;
    private Path mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        // 创建,并初始化Path
        mPath = new Path();
        mPath.moveTo(0, 0);
        for (int i = 1; i <= 15; i++) {
            // 生成15个点,随机生成它们的坐标,并将它们连成一条Path
            mPath.lineTo(i * 40, (float) Math.random() * 60);
        }
        // 初始化七个颜色
        mColors = new int[]{
                Color.BLACK, Color.BLUE, Color.CYAN,
                Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW
        };
    }

    @OnClick(R.id.button_draw)
    public void onClick() {
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 将背景填充成白色
        canvas.drawColor(Color.WHITE);
        // -------下面开始初始化7种路径的效果
        // 使用路径效果－－原始效果
        mEffects[0] = null;
        // 使用CornerPathEffect路径效果--参数为圆角半径
        mEffects[1] = new CornerPathEffect(10);
        // 初始化DiscretePathEffect －－ segmentLength指定最大的段长，deviation指定偏离量
        mEffects[2] = new DiscretePathEffect(3.0f, 5.0f);
        // 初始化DashPathEffect －－intervals为虚线的ON和OFF数组，offset为绘制时的偏移量
        mEffects[3] = new DashPathEffect(new float[]{
                20, 10, 5, 10
        }, mPhase);
        // 初始化PathDashPathEffect
        Path p = new Path();
        p.addRect(0, 0, 8, 8, Path.Direction.CCW);
        // shape则是指填充图形，advance指每个图形间的间距，phase为绘制时的偏移量，style为该类自由的枚举值
        mEffects[4] = new PathDashPathEffect(p, 12, mPhase, PathDashPathEffect.Style.ROTATE);
        // 组合效果
        mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]);
        // 叠加效果
        mEffects[6] = new SumPathEffect(mEffects[4], mEffects[3]);
        // 将画布移到8,8处开始绘制
        canvas.translate(8, 8);
        // 依次使用7种不同路径效果,7种不同的颜色来绘制路径
        for (int i = 0; i < mEffects.length; i++) {
            mPaint.setPathEffect(mEffects[i]);
            mPaint.setColor(mColors[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 60);
        }
        // 改变phase值,形成动画效果
        mPhase += 10;
        imageviewShow.setImageBitmap(bitmap);
    }
}

package com.uniquedu.mybitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class XmodeActivity extends AppCompatActivity {

    @InjectView(R.id.imageview_show)
    ImageView imageviewShow;
    @InjectView(R.id.button_normal)
    Button buttonNormal;
    @InjectView(R.id.button_clear)
    Button buttonClear;
    @InjectView(R.id.button_src)
    Button buttonSrc;
    @InjectView(R.id.button_dst)
    Button buttonDst;
    @InjectView(R.id.button_srcover)
    Button buttonSrcover;
    @InjectView(R.id.button_dstover)
    Button buttonDstover;
    @InjectView(R.id.button_srcin)
    Button buttonSrcin;
    @InjectView(R.id.button_dstin)
    Button buttonDstin;
    @InjectView(R.id.button_srcout)
    Button buttonSrcout;
    @InjectView(R.id.button_dstout)
    Button buttonDstout;
    @InjectView(R.id.button_srcatop)
    Button buttonSrcatop;
    @InjectView(R.id.button_dstatop)
    Button buttonDstatop;
    @InjectView(R.id.button_xor)
    Button buttonXor;
    @InjectView(R.id.button_darken)
    Button buttonDarken;
    @InjectView(R.id.button_lighten)
    Button buttonLighten;
    @InjectView(R.id.button_multiply)
    Button buttonMultiply;
    @InjectView(R.id.button_screen)
    Button buttonScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmode);
        ButterKnife.inject(this);
    }


    private void drawMode(PorterDuff.Mode mode) {
        //创建最终绘制的图片
        Bitmap bitmap = Bitmap.createBitmap(imageviewShow.getWidth(), imageviewShow.getHeight(), Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(bitmap);
        //画布绘制底色
        canvas.drawColor(Color.GRAY);
        //得到一个宽度
        int radio = imageviewShow.getHeight() / 4 * 3;
        //保存图层，在此图层进行操作
        int sc = canvas.saveLayer(0, 0, imageviewShow.getWidth(), imageviewShow.getHeight(), null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        //创建画笔
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        //绘制目标图
        canvas.drawBitmap(makeDst(radio, radio), 0, 0, paint);
        if (mode != null) {
            //设置叠加模式
            paint.setXfermode(new PorterDuffXfermode(mode));
        }
        //绘制源图
        canvas.drawBitmap(makeSrc(radio, radio), 0, 0, paint);
        //恢复图层
        canvas.restoreToCount(sc);
        imageviewShow.setImageBitmap(bitmap);
    }


    /**
     * 创建圆形图片
     *
     * @param w
     * @param h
     * @return
     */
    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
        return bm;
    }

    /**
     * 创建矩形图片
     *
     * @param w
     * @param h
     * @return
     */
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
        return bm;
    }

    @OnClick({R.id.button_normal, R.id.button_clear, R.id.button_src, R.id.button_dst, R.id.button_srcover, R.id.button_dstover, R.id.button_srcin, R.id.button_dstin, R.id.button_srcout, R.id.button_dstout, R.id.button_srcatop, R.id.button_dstatop, R.id.button_xor, R.id.button_darken, R.id.button_lighten, R.id.button_multiply, R.id.button_screen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_normal:
                drawMode(null);
                break;
            case R.id.button_clear:
                drawMode(PorterDuff.Mode.CLEAR);
                break;
            case R.id.button_src:
                drawMode(PorterDuff.Mode.SRC);
                break;
            case R.id.button_dst:
                drawMode(PorterDuff.Mode.DST);
                break;
            case R.id.button_srcover:
                drawMode(PorterDuff.Mode.SRC_OVER);
                break;
            case R.id.button_dstover:
                drawMode(PorterDuff.Mode.DST_OVER);
                break;
            case R.id.button_srcin:
                drawMode(PorterDuff.Mode.SRC_IN);
                break;
            case R.id.button_dstin:
                drawMode(PorterDuff.Mode.DST_IN);
                break;
            case R.id.button_srcout:
                drawMode(PorterDuff.Mode.SRC_OUT);
                break;
            case R.id.button_dstout:
                drawMode(PorterDuff.Mode.DST_OUT);
                break;
            case R.id.button_srcatop:
                drawMode(PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.button_dstatop:
                drawMode(PorterDuff.Mode.DST_ATOP);
                break;
            case R.id.button_xor:
                drawMode(PorterDuff.Mode.XOR);
                break;
            case R.id.button_darken:
                drawMode(PorterDuff.Mode.DARKEN);
                break;
            case R.id.button_lighten:
                drawMode(PorterDuff.Mode.LIGHTEN);
                break;
            case R.id.button_multiply:
                drawMode(PorterDuff.Mode.MULTIPLY);
                break;
            case R.id.button_screen:
                drawMode(PorterDuff.Mode.SCREEN);
                break;
        }
    }
}

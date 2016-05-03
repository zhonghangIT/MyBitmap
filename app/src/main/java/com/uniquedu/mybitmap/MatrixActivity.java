package com.uniquedu.mybitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 用于演示Canvas中图片的矩阵变化的界面
 */
public class MatrixActivity extends AppCompatActivity implements View.OnClickListener {

    private android.widget.ImageView imageviewshow;
    private android.widget.ImageView imageviewbottom;
    private android.widget.Button buttonshow;
    private android.widget.Button buttonrotate;
    private android.widget.Button buttontranscation;
    private Button buttonskew;
    private Button buttonsymmetry;
    private Bitmap mBitmap;
    private Button buttonscale;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        this.buttonscale = (Button) findViewById(R.id.button_scale);
        buttonscale.setOnClickListener(this);
        this.buttonsymmetry = (Button) findViewById(R.id.button_symmetry);
        buttonsymmetry.setOnClickListener(this);
        this.buttonskew = (Button) findViewById(R.id.button_skew);
        buttonskew.setOnClickListener(this);
        this.buttontranscation = (Button) findViewById(R.id.button_transcation);
        buttontranscation.setOnClickListener(this);
        this.buttonrotate = (Button) findViewById(R.id.button_rotate);
        buttonrotate.setOnClickListener(this);
        this.buttonshow = (Button) findViewById(R.id.button_show);
        buttonshow.setOnClickListener(this);
        this.imageviewbottom = (ImageView) findViewById(R.id.imageview_bottom);
        this.imageviewshow = (ImageView) findViewById(R.id.imageview_show);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.meinv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show:
                //显示原图
                imageviewshow.setImageResource(R.mipmap.meinv);
                break;
            case R.id.button_rotate:
                rotate();
                break;
            case R.id.button_transcation:
                translate();
                break;
            case R.id.button_skew:
                //显示错切后的图,错切调用的是set
                skew();

                break;
            case R.id.button_symmetry:
                //显示对称的图
                symmetry();
                break;
            case R.id.button_scale:
                scale();
                break;

        }
    }

    private void scale() {
        //生成一个和资源图一样大小的Bitmap，上面没有颜色
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //生成Canvas画布,该画布的大小与bitmap一样大
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        //将图片放大
        matrix.postScale(2, 2);
        //绘制图片mBitmap到画布
        canvas.drawBitmap(mBitmap, matrix, null);
        imageviewbottom.setImageBitmap(bitmap);
    }

    private void symmetry() {
        index++;
        //生成一个和资源图一样大小的Bitmap，上面没有颜色
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //生成Canvas画布,该画布的大小与bitmap一样大
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        float matrix_values[] = null;
        if (index % 2 == 0) {
            matrix_values = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
            //将图片上下对称变换
            matrix.setValues(matrix_values);
            //上下对称，会将图片对称到上方，下移图片
            matrix.postTranslate(0, mBitmap.getHeight());
        } else {
            matrix_values = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
            //将图片左右对称变换
            matrix.setValues(matrix_values);
            //左右对称会与Y轴对称，所以右移
            matrix.postTranslate(mBitmap.getWidth(), 0);
        }

        //绘制图片mBitmap到画布
        canvas.drawBitmap(mBitmap, matrix, null);
        imageviewbottom.setImageBitmap(bitmap);
    }

    private void skew() {
        //生成一个和资源图一样大小的Bitmap，上面没有颜色
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth() * 2, mBitmap.getHeight() * 2, Bitmap.Config.ARGB_8888);
        //生成Canvas画布,该画布的大小与bitmap一样大
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(mBitmap, matrix, null);
        //将图片错切,错切就是图像的投影
        matrix.setSkew(0f, 0.5f);
        //绘制图片mBitmap到画布

        canvas.drawBitmap(mBitmap, matrix, null);
        imageviewbottom.setImageBitmap(bitmap);
    }

    private void translate() {
        //显示位移后的图
        //生成一个和资源图一样大小的Bitmap，上面没有颜色
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //生成Canvas画布,该画布的大小与bitmap一样大
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        //将图片向右移动了一半的距离
        matrix.postTranslate(mBitmap.getWidth() / 2, 0);
        //绘制图片mBitmap到画布
        canvas.drawBitmap(mBitmap, matrix, null);
        imageviewbottom.setImageBitmap(bitmap);
    }

    private void rotate() {
        //显示旋转180度
        //生成一个和资源图一样大小的Bitmap，上面没有颜色
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //生成Canvas画布,该画布的大小与bitmap一样大
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        //矩阵旋转180度，以图片的中心旋转
        matrix.postRotate(180, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        //绘制图片mBitmap到画布
        canvas.drawBitmap(mBitmap, matrix, null);
        imageviewbottom.setImageBitmap(bitmap);
    }
}

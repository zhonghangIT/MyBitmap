package com.uniquedu.mybitmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.button_bitmap)
    Button buttonBitmap;
    @InjectView(R.id.button_canvas)
    Button buttonCanvas;
    @InjectView(R.id.button_paint)
    Button buttonPaint;
    @InjectView(R.id.button_matrix)
    Button buttonMatrix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.button_bitmap, R.id.button_canvas, R.id.button_paint, R.id.button_matrix})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_bitmap:
                startActivity(new Intent(getApplicationContext(), BitmapActivity.class));
                break;
            case R.id.button_canvas:
                startActivity(new Intent(getApplicationContext(), CanvasActivity.class));
                break;
            case R.id.button_paint:
                startActivity(new Intent(getApplicationContext(), PaintActivity.class));
                break;
            case R.id.button_matrix:
                startActivity(new Intent(getApplicationContext(), MatrixActivity.class));
                break;
        }
    }
}

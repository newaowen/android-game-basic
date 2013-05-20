package com.warsong.game.draw;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends Activity {

    protected DrawView mView;
    protected Paint mPaint;
    protected RectF ovalRectF;
    protected RectF circleRect;
    protected int screenWidth;
    protected int screenHeight;
    private int i;                // 弧形角度

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = 0;
        mView = new DrawView(this);
        setContentView(mView);

        mPaint = new Paint();
        ovalRectF = new RectF(160, 220, 560, 420);

        circleRect = new RectF(160, 500, 560, 900);

        DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();

        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

    }

    protected class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 设定绘图样式
            mPaint.setColor(0xff000000); // 画笔颜色
            mPaint.setAntiAlias(true); // 反锯齿

            // text
            mPaint.setStrokeWidth(1);
            mPaint.setTextSize(40);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText("分辨率: " + screenWidth + ", " + screenHeight, 50, 80, mPaint);

            // rect
            mPaint.setColor(0x55008800); // 画笔颜色
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);
            canvas.drawRect(ovalRectF, mPaint);

            // 绘制一个弧形
            mPaint.setColor(0xffff0000); // 画笔颜色
            canvas.drawArc(ovalRectF, 0, i, true, mPaint);

            canvas.drawArc(circleRect, 0, -i, false, mPaint);


            // 弧形角度
            if ((i += 10) > 360) {
                i = 0;
            }

            // 重绘, 再一次执行onDraw 程序
            invalidate();
        }
    }
}

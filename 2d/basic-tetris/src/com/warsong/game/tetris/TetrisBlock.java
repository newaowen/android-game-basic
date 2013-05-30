package com.warsong.game.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 方块
 * @author zhanqu
 *
 */
public class TetrisBlock  {

    protected TetrisScene scene;

    // 对应区域中的行列索引(注：可以
    public int  xIndex;
    public int  yIndex;

    // 块是否为空，即未被占用
    public boolean  isEmpty;

    protected Paint paint;

    protected RectF rect;
    
    // 当前朝向(四个方向)
    protected float orient; 

    public TetrisBlock() {
        paint = new Paint();
        rect = new RectF();
    }

    public TetrisBlock(TetrisScene scene, int x, int y) {
        this.scene = scene;
        paint = new Paint();
        rect = new RectF();
        setPosition(x, y);
    }

    /**
     * 设置新位置并更新rect
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        xIndex = x;
        yIndex = y;
        // 更新rect
        RectF r = scene.getBlockRect(x, y);
        rect.set(r.left, r.top, r.right, r.bottom);
    }

    public TetrisScene getScene() {
        return scene;
    }

    public void setScene(TetrisScene scene) {
        this.scene = scene;
    }

    public int getYIndex() {
        return yIndex;
    }

    public void setYIndex(int yIndex) {
        this.yIndex = yIndex;
    }

    public int getXIndex() {
        return xIndex;
    }

    public void setXIndex(int xIndex) {
        this.xIndex = xIndex;
    }


    /**
     * 绘制block
     * @param canvas
     */
    public void draw(Canvas canvas) {
        if (!isEmpty) {
        	drawBlockRect(rect, canvas, paint);
        }
    }
    
    /**
	 * 绘制单个块代码
	 * @param rect
	 * @param canvas
	 */
	public static void drawBlockRect(RectF rect, Canvas canvas, Paint paint) {
		paint.setColor(Color.RED); // 画笔颜色
		paint.setAntiAlias(true); // 反锯齿

		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		canvas.drawRect(rect, paint);

		paint.setColor(Color.GREEN); // 画笔颜色
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(rect, paint);
	}
    
    public RectF getBoundRect() {
        return  rect;
    }

}

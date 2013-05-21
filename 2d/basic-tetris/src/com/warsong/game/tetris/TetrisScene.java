package com.warsong.game.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.warsong.game.basic.GameScene;

public class TetrisScene extends GameScene {

	protected Paint		paint;

	protected Canvas	canvas;

	protected RectF		frameRect;
	protected RectF		leftRect;
	protected RectF		rightRect;

	public TetrisScene(String name) {
		super(name);
	}

	public void onCreate() {
		// 创建局部对象
		// frameRect = new RectF(10, 10, 500, 500);
		leftRect = new RectF(10, 10, 400, 600);
		rightRect = new RectF(400, 10, 600, 600);
	}

	@Override
	public void draw(Canvas canvas) {
		// 绘制
		this.canvas = canvas;

		drawFrame();
		drawLeftBlock();
		drawRightRegion();
	}

	protected void drawFrame() {
		paint = new Paint();

		// paint.set
		paint.setColor(Color.BLUE); // 画笔颜色
		paint.setAntiAlias(true); // 反锯齿

		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);

		canvas.drawRect(leftRect, paint);
		canvas.drawRect(rightRect, paint);
	}

	protected void drawLeftBlock() {

	}

	protected void drawRightRegion() {

	}

}

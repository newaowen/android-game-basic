package com.warsong.game.tetris;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.warsong.game.basic.GameScene;
import com.warsong.game.helper.DeviceHelper;

public class TetrisScene extends GameScene {

	public final static String		TAG	= "TetrisScene";

	protected Paint					paint;

	// draw canvas
	protected Canvas				canvas;

	protected RectF					sceneRect;
	protected RectF					leftRect;
	protected RectF					rightRect;

	// 宽高比
	protected float					ratio;
	protected int					rowNum;
	protected int					columnNum;

	protected int					sceneWidth;
	protected int					sceneHeight;

	protected int					leftWidth;
	protected int					rightWidth;

	// 场景在surface中左上角位置
	protected int					startX;
	protected int					startY;

	// 基本块大小
	protected int					blockSize;

	protected TetrisShape			currentShape;

	// 用户输入控制　方向键, 右侧大按钮变形
	protected RectF					leftButtonRect;
	protected RectF					rightButtonRect;
	protected RectF					downButtonRect;
	protected RectF					rotateButtonRect;

	// 场景内方块是否占用的标识
	// protected ArrayList<TetrisPos>	filledBlocks;
	public int[][]					blockFillFlags;

	protected boolean				shiftLeftOnce;
	protected boolean				shiftRightOnce;
	protected boolean				rotateShapeOnce;
	protected boolean				shiftLeftFast;
	protected boolean				shiftRightFast;
	protected boolean				rotateShape;
	protected boolean				fallFast;
	protected boolean				fallOnce;
	
	protected int		level;
	protected int 		score;
	
	// 当前被填充的最大行数
	public int 		topFilledRow;

	public TetrisScene(String name) {
		super(name);
	}

	public void onCreate() {
		int deviceHeight = DeviceHelper.getDeviceHeightPixel();

		//ratio = 0.8f;
		rowNum = 22;
		columnNum = 16;
		topFilledRow = rowNum - 1;
		//filledBlocks = new ArrayList<TetrisPos>();
		blockFillFlags = new int[columnNum][];
		for (int i = 0; i < columnNum; i++) {
			blockFillFlags[i] = new int[rowNum];
		}

		// 场景左侧 宽高需确定
		blockSize = 36;
		sceneHeight = rowNum * blockSize;// (int) (0.6 * deviceHeight);
		leftWidth = (int) (columnNum * blockSize);
		rightWidth = 160;
		sceneWidth = leftWidth + rightWidth;

		// blockSize = sceneHeight / rowNum;
		shiftLeftFast = false;
		shiftRightFast = false;
		rotateShape = false;
		fallOnce = false;
		fallFast = false;

		// 创建局部对象
		startX = 20;
		startY = 20;

		leftRect = new RectF(startX, startY, startX + leftWidth, startY + sceneHeight);
		rightRect = new RectF(startX + leftWidth, startY, startX + sceneWidth, startY + sceneHeight);
		sceneRect = new RectF(startX, startY, startX + sceneWidth, startY + sceneHeight);

		int buttonWidth = 160;
		int buttonHeight = 120;
		int hudStartY = deviceHeight - 400;
		leftButtonRect = new RectF(10, hudStartY, 10 + buttonWidth, hudStartY + buttonHeight);
		rightButtonRect = new RectF(40 + buttonWidth, hudStartY, 40 + 2 * buttonWidth, hudStartY+ buttonHeight);
		downButtonRect = new RectF(80, hudStartY + buttonHeight + 20, 40 +  buttonWidth, hudStartY + 2*buttonHeight + 20);
		rotateButtonRect = new RectF(startX + leftWidth, hudStartY, sceneWidth, hudStartY + buttonHeight);

		generateShape();
	}

	public RectF getBlockRect(int xIndex, int yIndex) {
		int left = startX + xIndex * blockSize;
		int top = startY + yIndex * blockSize;
		return new RectF(left, top, left + blockSize, top + blockSize);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.i(TAG, "onTouch: " + event.getAction());

		float touchX = event.getX();
		float touchY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (leftButtonRect.contains(event.getX(), event.getY())) {
				shiftLeftOnce = true;
			} else if (rightButtonRect.contains(event.getX(), event.getY())) {
				shiftRightOnce = true;
			} else if (rotateButtonRect.contains(event.getX(), event.getY())) {
				rotateShapeOnce = true;
			} else if (downButtonRect.contains(event.getX(), event.getY())) {
				fallOnce = true;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			// currentShape.shiftLeft();
			break;

		case MotionEvent.ACTION_UP:
			shiftLeftFast = false;
			shiftRightFast = false;
			rotateShape = false;
			fallFast = false;
			break;
		}

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		float touchX = e.getX();
		float touchY = e.getY();

		Log.i(TAG, "on long press");
		if (leftButtonRect.contains(touchX, touchY)) {
			shiftLeftFast = true;
		} else if (rightButtonRect.contains(touchX, touchY)) {
			shiftRightFast = true;
		} else if (rotateButtonRect.contains(touchX, touchY)) {
			rotateShape = true;
		} else if (downButtonRect.contains(touchX, touchY)) {
			fallFast = true;
		}
	}

	protected void generateShape() {
		// 重新生成新shape
		currentShape = TetrisShapeFactory.createTetrisShapeRandom(this);
		//currentShape = TetrisShapeFactory.createTetrisShape(this, TetrisConstant.TYPE_I);
		currentShape.setPosition(columnNum / 2, 1);
	}

	// 渲染线程
	@Override
	public void updateScene() {
		// 更新当前　正在处理的shape
		// shape是否已经固定？
		if (currentShape.isFallStop()) {
			// 使用方块填充场景
			fillShape(currentShape);
			// 产生新方块
			generateShape();
		} else {
			if (shiftLeftFast || shiftLeftOnce) {
				currentShape.shiftLeft();
				shiftLeftOnce = false;
			} else if (shiftRightFast || shiftRightOnce) {
				currentShape.shiftRight();
				shiftRightOnce = false;
			} else if (rotateShape || rotateShapeOnce) {
				// 边界旋转会产生位移
				currentShape.rotate();
				rotateShapeOnce = false;
			} else if (fallFast || fallOnce) {
				currentShape.fallFast();
				fallOnce = false;
			}

			currentShape.fall();
		}
		// 更新shape内部block地址
		currentShape.updateBlocks();
		
		// 更新场景内消去行block
		updateRowFilledBlocks();
	}
	
	protected void updateRowFilledBlocks() {
		ArrayList<Integer> removeFillRows = new ArrayList<Integer>();
		// 从topFilledRow到 rowNum-1计算(注意，多个消去行可能不连续)
		for (int j = topFilledRow; j < rowNum; j ++) {
			boolean flag = true;
			for (int i = 0; i < columnNum; i++) {
				if (blockFillFlags[i][j] == 0) {
					flag = false;
					break;
				}
			}
			if (flag) { // 该行应该消去
				// 合并消去行 (from -> to)
				Log.i(TAG, "remove row: " + j);
				removeFillRows.add(j);
			}
		}
		
		// 合并消去行
		
		// 其他行移位
		// 计算效率不高　TODO 性能优化
		//int removedTopRow = topFilledRow;
		for (int removedRow : removeFillRows) {
			for (int r = removedRow; r >= topFilledRow; r--) {
				Log.i(TAG, "move row down from " + (r-1) + " to " + (r));
				for (int i = 0; i < columnNum; i++) {
					blockFillFlags[i][r] = blockFillFlags[i][r - 1]; // 数组越界?
				}
			}
			topFilledRow --;
			
			score += 100;
		}
		
		// 分数统计
	}

	protected void fillShape(TetrisShape shape) {
		Log.i(TAG, "fill scene");
		shape.fillScene();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		// 绘制
		this.canvas = canvas;
		paint = new Paint();
		paint.setAntiAlias(true); // 反锯齿

		drawFrame();
		drawControlHud();
		drawLeftBlock();
		drawRightRegion();

		drawShape();
		
		// 更新下层已填充方块
		drawFilledBlocks();
	}

	protected void drawFrame() {
		// 绘制场景
		paint.setColor(Color.TRANSPARENT); // 画笔颜色
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(sceneRect, paint);

		paint.setColor(Color.WHITE); // 画笔颜色
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);

		canvas.drawRect(leftRect, paint);
		canvas.drawRect(rightRect, paint);

		// 绘制小block
		// 绘制竖线
		int endY = sceneHeight; // blockSize * rowNum;
		int endX = columnNum * blockSize;
		for (int j = 0; j < columnNum; j++) {
			canvas.drawLine(startX + j * blockSize, startY, startX + j * blockSize, startY + endY, paint);
		}
		// 绘制横线
		for (int i = 0; i < rowNum; i++) {
			canvas.drawLine(startX, startY + i * blockSize, startX + endX, startY + i * blockSize, paint);
		}
	}

	protected void drawControlHud() {
		paint.setColor(Color.RED); // 画笔颜色
		paint.setStyle(Paint.Style.FILL);

		canvas.drawRect(leftButtonRect, paint);
		canvas.drawRect(rightButtonRect, paint);
		canvas.drawRect(downButtonRect, paint);

		paint.setColor(Color.WHITE); // 画笔颜色
		canvas.drawRect(rotateButtonRect, paint);
	}

	protected void drawLeftBlock() {

	}

	/**
	 * 绘制右边区域显示
	 */
	protected void drawRightRegion() {
		// draw next shape
		
		
		paint.setColor(Color.WHITE); // 画笔颜色
		paint.setTextSize(24);
		canvas.drawText("BasicTetris 0.1", startX + leftWidth + 20, startY + 40, paint);
		
		canvas.drawText("得分: " + score, startX + leftWidth + 20, startY + 120, paint);
		
		canvas.drawText("Level: " + level, startX + leftWidth + 20, startY + 220, paint);
	}

	protected void drawFilledBlocks() {
		for (int i = topFilledRow; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				if (blockFillFlags[j][i] > 0) {
					RectF rect = new RectF(startX + j * blockSize, startY + i * blockSize, startX + (j + 1)
							* blockSize, startY + (i + 1) * blockSize);
		 			TetrisBlock.drawBlockRect(rect, canvas, paint);
				}
			}
		}
			
//			PAINT.SETCOLOR(COLOR.RED); // 画笔颜色
//			PAINT.SETANTIALIAS(TRUE); // 反锯齿
//
//			PAINT.SETSTYLE(PAINT.STYLE.STROKE);
//			PAINT.SETSTROKEWIDTH(1);
//			CANVAS.DRAWRECT(RECT, PAINT);
//
//			PAINT.SETCOLOR(COLOR.GREEN); // 画笔颜色
//			PAINT.SETSTYLE(PAINT.STYLE.FILL);
//			CANVAS.DRAWRECT(RECT, PAINT);
			
			//Log.i(TAG, "drawFilledBlocks: " + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom);
		//}
	}

	protected void drawShape() {
		currentShape.draw(canvas);
	}

}
 
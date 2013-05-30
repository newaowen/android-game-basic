package com.warsong.game.tetris;

import android.graphics.Canvas;
import android.util.Log;

/**
 * 变换的方块形状，即被操作的组合块 Created by zhanqu on 13-5-22.
 */
public class TetrisShape {

	public final static String	TAG	= "TetrisShape";

	protected TetrisScene		scene;
	// 块元素
	protected TetrisBlock[]		blocks;
	// 形状详细描述(静态描述)
	protected TetrisShapeSpec	shapeSpec;
	// 当前位置 (需要支持浮点，在移动时平滑动画)
	protected float				xIndex;
	protected float				yIndex;

	// 跌落速度
	protected float				fallSpeed;
	protected float				speedCount;
	// 当前朝向
	protected int				orient;

	public TetrisShape(TetrisScene scene, TetrisShapeSpec spec) {
		speedCount = 0;
		fallSpeed = 3f;

		this.scene = scene;
		this.shapeSpec = spec;
		this.orient = TetrisConstant.ORIENT_NORTH;

		createBlocks();
	}

	protected void createBlocks() {
		if (shapeSpec != null) {
			int blockNum = shapeSpec.getBlockNum();
			blocks = new TetrisBlock[blockNum];
			// 根据spec创建block
			for (int i = 0; i < blockNum; i++) {
				int x = shapeSpec.getBlockXIndex(i, orient);
				int y = shapeSpec.getBlockYIndex(i, orient);
				blocks[i] = new TetrisBlock(scene, (int) (xIndex + x), (int) (yIndex + y));
			}
		}
	}

	public TetrisBlock[] getBlocks() {
		return blocks;
	}

	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}

	/**
	 * 获取blocks中最靠下的y(最大的)
	 */
	public int getBottomY() {
		int bottomY = 0;
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			int y = shapeSpec.getBlockYIndex(i, orient);
			bottomY = Math.max((int) (y + yIndex), bottomY);
		}
		return bottomY;
	}

	/**
	 * 设置shape地址
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		xIndex = x;
		yIndex = y;
	}

	/**
	 * 左移一格
	 */
	public void shiftLeft() {
		xIndex--;
		if (hitLeftTest() || hitSceneBlockTest()) {
			xIndex++;
		}
	}

	// public void shiftLeftFast() {
	// xIndex -= 2;
	// }

	public void shiftRight() {
		xIndex++;
		if (hitRightTest() || hitSceneBlockTest()) {
			xIndex--;
		}
	}

	// public void shiftRightFast() {
	// xIndex += 2;
	// }

	public void rotate() {
		orient++;
		if (orient == TetrisConstant.ORIENT_NUM) {
			orient = TetrisConstant.ORIENT_NORTH;
		}

		// 进行边界碰撞检测，是否超过left, right, bottom边界
		if (hitLeftTest() || hitBottomTest() || hitRightTest() || hitSceneBlockTest()) {

			if (orient == TetrisConstant.ORIENT_NORTH) {
				orient = TetrisConstant.ORIENT_WEST;
			} else {
				orient--;
			}
		}

	}

	public void fall() {
		if (speedCount > fallSpeed) {
			yIndex++;
			if (hitBottomTest() || hitSceneBlockTest()) {
				yIndex--;
			}
			speedCount = 0;
		} else {
			speedCount++;
		}
	}
	
	/**
	 * 快速下落
	 */
	public void fallFast() {
		yIndex++;
		if (hitBottomTest() || hitSceneBlockTest()) {
			yIndex--;
		}
	}

	// 碰撞检测
	/**
	 * 是否已固定在底部
	 * 
	 * @return
	 */
	public boolean isFallStop() {
		yIndex++;
		if (hitSceneBlockTest()) {
			yIndex--;
			return true;
		}
		yIndex--;

		// 判断是否到底部边界
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			if (caculateBlockY(i, (int) (yIndex)) >= (scene.rowNum - 1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测shape是否已经到底部
	 */
	public boolean hitBottomTest() {
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			if (caculateBlockY(i, (int) yIndex) > (scene.rowNum - 1)) {
				return true;
			}
		}
		return false;
	}

	protected boolean hitLeftTest() {
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			if (caculateBlockX(i, (int) xIndex) < 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean hitRightTest() {
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			if (caculateBlockX(i, (int) xIndex) > (scene.columnNum - 1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 与场景已填充block 进行碰撞检测
	 * @return
	 */
	protected boolean hitSceneBlockTest() {
		// 是否需要与已填充block做检测？
		if (scene.topFilledRow <= getBottomY()) {
			for (int k = 0; k < shapeSpec.getBlockNum(); k++) {
				int x = caculateBlockX(k, (int) xIndex);
				int y = caculateBlockY(k, (int) yIndex);
				for (int j = scene.topFilledRow; j < scene.rowNum; j++) {
					for (int i = 0; i < scene.columnNum; i++) {
						if (scene.blockFillFlags[i][j] > 0) {
							if (i == x && j == y) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * 更新block绝对位置if (scene.topFilledRow < getBottomY()) {
	 */
	public void updateBlocks() {
		// update blocks position
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			int x = shapeSpec.getBlockXIndex(i, orient);
			int y = shapeSpec.getBlockYIndex(i, orient);
			blocks[i].setPosition((int) (xIndex + x), (int) (yIndex + y));
		}
	}

	/**
	 * 形状最终固定后，将形状对应区域填充到场景中
	 */
	public void fillScene() {
		for (int i = 0; i < shapeSpec.getBlockNum(); i++) {
			int x = caculateBlockX(i, (int) xIndex);
			int y = caculateBlockY(i, (int) yIndex);
			scene.blockFillFlags[x][y] = 1;
			// TetrisPos pos = new TetrisPos(block.getXIndex(),
			// block.getYIndex());
			// scene.filledBlocks.add(pos);
			// Log.i(TAG, "shape fill scene: " + pos.x + ", " + pos.y);
			if (y < scene.topFilledRow) {
				scene.topFilledRow = y;
			}
		}
	}

	public void draw(Canvas canvas) {
		for (TetrisBlock block : blocks) {
			block.draw(canvas);
		}
	}

	protected int caculateBlockX(int i, int baseX) {
		return shapeSpec.getBlockXIndex(i, orient) + baseX;
	}

	protected int caculateBlockY(int i, int baseY) {
		return shapeSpec.getBlockYIndex(i, orient) + baseY;
	}

}

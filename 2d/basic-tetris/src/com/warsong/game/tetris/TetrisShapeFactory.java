package com.warsong.game.tetris;

import java.util.Random;

/**
 * 形状工厂 Created by zhanqu on 13-5-22.
 */
public class TetrisShapeFactory {


	// 各种形状的内block在四个朝向上的x,y偏移
	public static int[]		shapeSpecTable		= { 
			0, 0, 1, 0, 2, 0, 3, 0, // I
			1, -1, 1, 0, 1, 1, 1, 2, 
			0, 0, 1, 0, 2, 0, 3, 0, 
			1, -1, 1, 0, 1, 1, 1, 2, 

			0, 0, 0, 1, 1, 1, 2, 1, // J
			2, 0, 1, 0, 1, 1, 1, 2, // rotate 90
			0, 1, 1, 1, 2, 1, 2, 2, // rotate 180
			1, 2, 2, 2, 2, 1, 2, 0, // rotate 270

			0, 1, 1, 1, 2, 1, 2, 0, // L
			1, 0, 1, 1, 1, 2, 2, 2, 
			0, 1, 1, 1, 2, 1, 0, 2, 
			0, 0, 1, 0, 1, 1, 1, 2, 
			
			0, 0, 1, 0, 1, 1, 0, 1, // O
			0, 0, 1, 0, 1, 1, 0, 1, 
			0, 0, 1, 0, 1, 1, 0, 1, 
			0, 0, 1, 0, 1, 1, 0, 1, 
			
			0, 1, 1, 1, 1, 0, 2, 0, // s
			0, 0, 0, 1, 1, 1, 1, 2,
			0, 1, 1, 1, 2, 1, 1, 2,
			0, 0, 0, 1, 1, 1, 1, 2,
			
			0, 1, 1, 1, 1, 0, 2, 1, // T
			//2, 0, 2, 1, 1, 1, 1, 2,
			1, 0, 1, 1, 1, 2, 2, 1,
			0, 1, 1, 1, 2, 1, 1, 2,
			1, 0, 1, 1, 1, 2, 0, 1,
			
			0, 0, 1, 0, 1, 1, 2, 1, // Z
			2, 0, 2, 1, 1, 1, 1, 2,
			0, 0, 1, 0, 1, 1, 2, 1,
			2, 0, 2, 1, 1, 1, 1, 2,
	};

	public static TetrisShape createTetrisShapeRandom(TetrisScene scene) {
		int i = new Random().nextInt(TetrisConstant.TOTAL_TYPE);
		return createTetrisShape(scene, i);
	}

	public static TetrisShape createTetrisShape(TetrisScene scene, int type) {
		int blockNum = TetrisConstant.DEFAULT_BLOCK_NUM;
		int[] xOffset = new int[blockNum * TetrisConstant.ORIENT_NUM];
		int[] yOffset = new int[blockNum * TetrisConstant.ORIENT_NUM];

		int start = type * blockNum * 2 * TetrisConstant.ORIENT_NUM;
		for (int j = 0; j < TetrisConstant.ORIENT_NUM; j++) {
			for (int i = 0; i < blockNum; i++) {
				xOffset[j*TetrisConstant.ORIENT_NUM + i] = shapeSpecTable[start + j * blockNum * 2 + i * 2];
				yOffset[j*TetrisConstant.ORIENT_NUM + i] = shapeSpecTable[start + j * blockNum * 2 + i * 2 + 1];
			}
		}

		TetrisShapeSpec spec = new TetrisShapeSpec(xOffset, yOffset);
		return new TetrisShape(scene, spec);
	}

}

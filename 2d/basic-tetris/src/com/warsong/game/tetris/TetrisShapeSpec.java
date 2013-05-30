package com.warsong.game.tetris;

/**
 * 描述方块形状特征的数据结构
 * Created by zhanqu on 13-5-22.
 */
public class TetrisShapeSpec {

    protected int blockNum;
    protected int type;

    // 内部block偏移 
    protected int[] xOffsets;
    protected int[] yOffsets;
    
    public TetrisShapeSpec(int[] xOffsets, int[] yOffsets) {
        this.blockNum = xOffsets.length / TetrisConstant.ORIENT_NUM;
        this.xOffsets = xOffsets;
        this.yOffsets = yOffsets;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public int getBlockXIndex(int i, int orient) {
        return xOffsets[i + orient * TetrisConstant.ORIENT_NUM];
    }

    public int getBlockYIndex(int i, int orient) {
        return yOffsets[i + orient * TetrisConstant.ORIENT_NUM];
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

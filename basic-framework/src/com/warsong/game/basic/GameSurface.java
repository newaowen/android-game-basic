package com.warsong.game.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 绘制表面
 * Created by zhanqu on 13-5-20.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    protected SurfaceHolder holder;

    public GameSurface(Context context) {
        super(context);
        onCreate();
    }

    public void onCreate() {
        holder = getHolder();
        holder.addCallback(this);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    	// 初始化game looper线程(android框架限制，必须在这里启动)
    	GameApp.getInstance().surfaceCreatedCallback();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
     
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    	GameApp.getInstance().surfaceDestroyedCallback();
    }
    
    public SurfaceHolder getSurfaceHolder() {
    	return holder;
    }

}

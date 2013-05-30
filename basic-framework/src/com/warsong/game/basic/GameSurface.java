package com.warsong.game.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 绘制表面
 * Created by zhanqu on 13-5-20.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    protected SurfaceHolder holder;
    
    protected GestureDetector gd;
    
    public GameSurface(Context context) {
        super(context);
        onCreate();
    }

    public void onCreate() {
        holder = getHolder();
        holder.addCallback(this);
    }
    
    // 绑定使用app的touch和手势监听 
    public void bindTouchListener() {
    	this.setLongClickable(true);
        gd = new GestureDetector(getContext(), GameApp.getInstance().getGestureListener());
        gd.setIsLongpressEnabled(true);
        // set touch listener
        // setOnTouchListener(GameApp.getInstance().getOnTouchListener());
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	gd.onTouchEvent(event);
    	GameApp.getInstance().getOnTouchListener().onTouch(this, event);
    	
    	return true;
//        if (event.getAction() == ACTION_UP) {
//               // long press up detected
//        }
//        if (gd.onTouchEvent(event)) {
//            return true;
//        } else {
//        	GameApp.getInstance().getOnTouchListener()
//            return false;
//        }
    }
    
}

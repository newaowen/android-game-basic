package com.warsong.game.basic;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Canvas;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 游戏场景基类
 * Created by zhanqu on 13-5-20.
 */
public class GameScene implements OnGestureListener, OnTouchListener {

    protected String name;

    // 绘制区域（暂时未使用)
    protected Map<String, SceneRegion> regions;
  

    public GameScene() {
        regions = new HashMap<String, SceneRegion>();
        onCreate();
    }

    public GameScene(String name) {
        regions = new HashMap<String, SceneRegion>();
        this.name = name;

        onCreate();
    }

    public void onCreate() {

    }

    public String getName() {
        return name;
    }
    
    public void render() {
	   updateScene();
       renderOneFrame();
    }

    /**
     * 需覆盖
     * 更新场景内物体　位置，大小，颜色，状态等等
     */
    public void updateScene() {

    }

    /**
     * 需覆盖
     * 场景 单帧渲染函数
     */
	public void renderOneFrame() {
		SurfaceHolder holder = GameApp.getInstance().getSurfaceHolder();
		Canvas canvas = holder.lockCanvas(null);
		if (canvas != null) {
			draw(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	/**
	 * 实际每帧绘制
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}

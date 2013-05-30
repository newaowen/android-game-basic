package com.warsong.game.basic;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceHolder;

/**
 * Created by zhanqu on 13-5-20.
 */
public class GameContext {

	// 常量
	public final static float DEFAULT_FPS = 30; 
	
	
	// android activity
	protected Activity activity;
	
    // 绘制表面
    protected GameSurface surface;

    // android context
    protected Context    context;
	
    // 场景管理
	protected GameSceneManager  sceneManager;



    // looper
	protected GameLooper  gameLooper;
	
    public GameContext() {
    	 
    }
    
    public void attachActivity(Activity activity) {
    	this.activity = activity;
    }
    
    
    public GameSurface getSurface() {
		return surface;
	}

	public void setSurface(GameSurface surface) {
		this.surface = surface;
	}
	
	public SurfaceHolder getSurfaceHolder() {
		return surface.getSurfaceHolder();
	}
	
    public Activity getActivity() {
        return activity;
    }

    public Context getActivityContext() {
        return activity;
    }

    public Context getActivityBaseContext() {
        return activity.getBaseContext();
    }
    
    public GameSceneManager getSceneManager() {
        return sceneManager;
    }

    public GameLooper getGameLooper() {
        return gameLooper;
    }

}

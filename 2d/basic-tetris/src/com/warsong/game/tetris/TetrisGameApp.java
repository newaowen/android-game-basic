package com.warsong.game.tetris;

import android.app.Activity;

import android.view.MotionEvent;
import com.warsong.game.basic.GameApp;
import com.warsong.game.basic.GameScene;

/**
 * Created by zhanqu on 13-5-20.
 */
public class TetrisGameApp extends GameApp {

	public TetrisGameApp(Activity activity) {
		super(activity);
	}
	
	@Override
    protected void onCreate() {
    	super.onCreate();
        TetrisScene scene = new TetrisScene("main-scene");
        sceneManager.addScene(scene, true);
        
        // 必须在此初始化事件监听
        this.setGestureListener(scene);
        this.setOnTouchListener(scene);
    }

    public GameScene getMainScene() {
        return sceneManager.getStartScene();
    }

}

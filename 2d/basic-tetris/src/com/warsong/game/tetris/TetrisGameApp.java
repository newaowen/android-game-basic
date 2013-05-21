package com.warsong.game.tetris;

import android.app.Activity;

import com.warsong.game.basic.GameApp;

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
    }

}

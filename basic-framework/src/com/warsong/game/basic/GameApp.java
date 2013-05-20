package com.warsong.game.basic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 游戏app
 * Created by zhanqu on 13-5-20.
 */
public class GameApp {

    protected Activity activity;

    protected GameSceneManager  sceneManager;
    protected Context    context;

    public GameApp(Activity activity) {
        this.activity = activity;

        sceneManager = new GameSceneManager();
        GameContext.getInstance().setApp(this);
    }

    public Activity getActivity() {
        return activity;
    }

    public Context getActivityContext() {
        return activity;
    }

    public GameSceneManager getSceneManager() {
        return sceneManager;
    }

    public void start() {

    }
}

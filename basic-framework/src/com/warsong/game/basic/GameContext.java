package com.warsong.game.basic;

/**
 * Created by zhanqu on 13-5-20.
 */
public class GameContext {

    protected static GameContext instance;

    protected static GameApp app;

    public static GameApp getApp() {
        return app;
    }

    public void setApp(GameApp app) {
        this.app = app;
    }

    private GameContext() {

    }

    public synchronized static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }

        return instance;
    }

}

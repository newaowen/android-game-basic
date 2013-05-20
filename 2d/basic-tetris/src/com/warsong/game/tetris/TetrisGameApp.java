package com.warsong.game.tetris;

import android.app.Activity;
import android.content.Context;
import com.warsong.game.basic.GameApp;
import com.warsong.game.basic.GameContext;

/**
 * Created by zhanqu on 13-5-20.
 */
public class TetrisGameApp extends GameApp {

    public TetrisGameApp(Activity activity) {
        super(activity);

        TetrisScene scene = new TetrisScene("main-scene");
        sceneManager.addScene(scene, true);
    }


    /**
     * 启动游戏
     */
    public void start() {
        sceneManager.getStartScene().start();
    }


}

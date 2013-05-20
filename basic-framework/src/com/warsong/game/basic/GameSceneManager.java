package com.warsong.game.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanqu on 13-5-20.
 */
public class GameSceneManager {

    protected Map<String, GameScene> gameScenes;
    protected GameScene startScene;

    public GameSceneManager() {
        this.gameScenes = new HashMap<String, GameScene>();
    }

    public void addScene(GameScene scene) {
        gameScenes.put(scene.getName(), scene);
    }

    public void addScene(GameScene scene, boolean isStart) {
        gameScenes.put(scene.getName(), scene);

        if (isStart) {
            startScene = scene;
        }
    }

    /**
     * 获取开始场景
     * @return
     */
    public GameScene getStartScene() {
        return startScene;
    }


}

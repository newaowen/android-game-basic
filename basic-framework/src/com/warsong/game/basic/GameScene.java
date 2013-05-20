package com.warsong.game.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏基本场景管理
 * Created by zhanqu on 13-5-20.
 */
public class GameScene {

    protected String name;

    protected Map<String, SceneRegion> regions;

    public GameScene() {
        regions = new HashMap<String, SceneRegion>();
    }

    public GameScene(String name) {
        regions = new HashMap<String, SceneRegion>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void start() {

    }

}

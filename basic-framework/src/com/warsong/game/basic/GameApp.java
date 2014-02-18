package com.warsong.game.basic;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;


/**
 * 游戏app
 * Created by zhanqu on 13-5-20.
 */
public class GameApp extends GameContext {

    // 全局唯一
    protected static GameApp instance;

    // 绘制表面变化监听
    protected SurfaceListener surfaceListener;

    // 手势监听
    protected OnGestureListener gestureListener;

    protected OnTouchListener onTouchListener;

    public static GameApp getInstance() {
        return instance;
    }

    public GameApp(Activity activity) {
        super();
        attachActivity(activity);
        onCreate();
    }

    protected void onCreate() {
        instance = this;
        // 初始化场景管理器
        sceneManager = new GameSceneManager();
        // 初始化looper
        gameLooper = new GameLooper();
        // 设置默认surface变化事件监听
        surfaceListener = defaultSurfaceListener;

        loadDefaultConfig();
        // TODO dispatch message
    }

    /**
     * 手动创建并初始化表面
     */
    public void createSurface() {
        surface = new GameSurface(activity);
        surface.bindTouchListener();
        activity.setContentView(surface);
    }

    /**
     * TODO
     * 加载默认配置
     */
    public void loadDefaultConfig() {
        if (gameLooper != null) {
            gameLooper.setFps(GameContext.DEFAULT_FPS);
        }
    }

    /**
     * 开始
     */
    public void start() {
        if (gameLooper != null) {
            gameLooper.interrupt();
        } else {
            gameLooper = new GameLooper();
        }

        gameLooper.start();
        // TODO dispatch message
    }

    public void stop() {
        gameLooper.interrupt();
        gameLooper = null;
        // TODO dispatch message
    }

    protected void onStop() {
        // TODO dispatch message
    }

    /**
     * 每帧的渲染调用
     */
    protected void onRender() {
        GameScene scene = sceneManager.getStartScene();
        if (scene != null) {
            scene.render();
        }
        // TODO dispatch message
    }

    /**
     * 触屏事件监听器
     *
     * @param event
     */
    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public OnGestureListener getGestureListener() {
        return gestureListener;
    }

    public void setGestureListener(OnGestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }

    public SurfaceListener getSurfaceListener() {
        return surfaceListener;
    }

    public void setSurfaceListener(SurfaceListener surfaceListener) {
        this.surfaceListener = surfaceListener;
    }

    /**
     * 在surface创建时调用
     * 不要手动调用
     */
    public void surfaceCreatedCallback() {
        if (surfaceListener != null) {
            surfaceListener.onSurfaceCreate();
        }
    }

    /**
     * 在surface销毁时调用
     * 不要手动调用
     */
    public void surfaceDestroyedCallback() {
        if (surfaceListener != null) {
            surfaceListener.onSurfaceDestory();
        }
    }

    protected SurfaceListener defaultSurfaceListener = new SurfaceListener() {
        @Override
        public void onSurfaceDestory() {
            stop();
        }

        @Override
        public void onSurfaceCreate() {
            start();
        }
    };


    // android 表面变化回调接口定义
    public interface SurfaceListener {
        public void onSurfaceCreate();

        public void onSurfaceDestory();
    }

}

package com.warsong.game.basic;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * game loop
 * Created by zhanqu on 13-5-21.
 */
public class GameLooper extends Thread {

    // 是否正在运行标志
    protected boolean isRunning;

    protected float fps;
    // frame duration time per frame (unit: 毫秒)
    protected long frameDuration;

    protected Handler handler;

    public GameLooper() {
        handler = new Handler(Looper.myLooper());
    }

    public float getFps() {
        return fps;
    }

    public void dispatchMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setFps(float fps) {
        this.fps = fps;
        frameDuration = (long) (1000.f / fps);
    }
        
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        GameApp app = GameApp.getInstance();
        
        while (true) {
        	app.onRender();

            try {
                // fps control
                long leftTime = frameDuration - (System.currentTimeMillis() - startTime);
                if (leftTime > 0) {
                    sleep(leftTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            startTime = System.currentTimeMillis();
        }
    }
}

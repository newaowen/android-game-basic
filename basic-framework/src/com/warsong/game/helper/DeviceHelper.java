package com.warsong.game.helper;

import android.util.DisplayMetrics;
import com.warsong.game.basic.GameApp;

public class DeviceHelper {

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = GameApp.getInstance().getActivityBaseContext().getResources().getDisplayMetrics();
        return metrics;
    }

    public static int getDeviceWidthPixel() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getDeviceHeightPixel() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int pixelToDp(int pixel) {
    	return 0;
    }
    
    public static int dpToPixel(int dp) {
    	return 0;
    }
}

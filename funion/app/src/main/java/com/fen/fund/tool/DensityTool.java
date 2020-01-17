package com.fen.fund.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DensityTool {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @return px
     */
    public static int getScreenWidth(Context context) {
        Display display = getDisplay(context);
        if (display == null) {
            return 0;
        }
        Point outSize = new Point();
        display.getRealSize(outSize);
        return outSize.x;
    }

    /**
     * 获取屏幕高度
     * @return px
     */
    public static int getScreenHeight(Context context) {
        Display display = getDisplay(context);
        if (display == null) {
            return 0;
        }
        Point outSize = new Point();
        display.getRealSize(outSize);
        return outSize.y;
    }

    /**
     * Get Display
     * @param context Context for get WindowManager
     * @return Display
     */
    private static Display getDisplay(Context context) {
        WindowManager wm;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            wm = activity.getWindowManager();
        } else {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (wm != null) {
            return wm.getDefaultDisplay();
        }
        return null;
    }

}

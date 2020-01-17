package com.fen.fund.ui.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.util.Objects;

/**
 * 全局Application配置
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        QMUISwipeBackActivityManager.init(this);
//        FastSharedPreferences.init(this);
    }

    public static App getInstance() {
        return instance;
    }

    public long versionCode() {
        Context context = getApplicationContext();
        PackageManager manager = context.getPackageManager();
        long code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return code;
    }

}

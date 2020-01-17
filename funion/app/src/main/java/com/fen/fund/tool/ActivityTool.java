package com.fen.fund.tool;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity工具类
 */
public class ActivityTool {
    private List<Activity> activities = new ArrayList<>();
    private static ActivityTool instance;

    /** 单例模式中获取唯一的ExitApplication实例 */
    public static synchronized ActivityTool getInstance() {
        if (null == instance) {
            instance = new ActivityTool();
        }
        return instance;
    }

    /** 添加Activity到容器中 */
    public void addActivity(Activity activity) {
        if (activities == null)
            activities = new ArrayList<>();
        activities.add(activity);
    }

    /** 移除Activity */
    public void removeActivity(Activity activity) {
        if (activities != null)
            activities.remove(activity);
    }

    /** 遍历所有Activity并finish */
    public void exitSystem() {
        for (Activity activity : activities) {
            if (activity != null)
                activity.finish();
        }
        // 退出进程
        android.os.Process.killProcess(Process.myPid());
        System.exit(0);
    }

}
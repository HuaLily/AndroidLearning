package com.huawenli.broadcast;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {
    public  static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    //直接调用这个 可以随时随地退出程序
    public static void finishAll(){
        for (Activity activity :activityList) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activityList.clear();
    }
}

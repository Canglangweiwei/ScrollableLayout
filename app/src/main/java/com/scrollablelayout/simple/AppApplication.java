package com.scrollablelayout.simple;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.Stack;

/**
 * APPLICATION
 */
@SuppressWarnings("ALL")
public class AppApplication extends Application {

    public static AppApplication sApp;

    private Stack<Activity> activityStack;          // activity栈

    public static AppApplication get(Context context) {
        return (AppApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    /**
     * 把一个activity压入栈列中
     *
     * @param actvity
     */
    public void pushActivityToStack(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }

    /**
     * 获取栈顶的activity，先进后出原则
     */
    public Activity getLastActivityFromStack() {
        return activityStack.lastElement();
    }

    /**
     * 从栈列中移除一个activity
     *
     * @param activity
     */
    public void popActivityFromStack(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 退出所有activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivityFromStack();
                if (activity == null) {
                    break;
                }
                popActivityFromStack(activity);
            }
        }
    }
}

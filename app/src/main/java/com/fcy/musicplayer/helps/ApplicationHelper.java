package com.fcy.musicplayer.helps;


import android.annotation.SuppressLint;

import com.fcy.musicplayer.base.MyApplication;

import java.lang.reflect.InvocationTargetException;

public class ApplicationHelper {
    public static MyApplication getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (MyApplication) app;
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}
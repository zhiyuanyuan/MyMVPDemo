package com.zhiyuan.mymvpdemo.base;

import android.app.Application;

/**
 * Created by admin on 2017/11/30.
 */

public class AppOS extends Application {
      public static AppOS appOS;
    @Override
    public void onCreate() {
        super.onCreate();
        appOS=this;
        UniException.getInstance().init();
    }
}

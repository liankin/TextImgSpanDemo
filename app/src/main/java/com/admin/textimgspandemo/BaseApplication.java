package com.admin.textimgspandemo;

import android.app.Application;
import android.support.think.util.CacheUtil;
import android.support.think.util.CrashHandler;

import org.xutils.x;

/**
 * Created by admin on 2018/1/19.
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //初始化xUtils
        x.Ext.init(this);
        //异常捕获
        CacheUtil.build(this);
        CrashHandler.build(this);
    }
    public static BaseApplication getAppContext() {
        return mInstance;
    }
}

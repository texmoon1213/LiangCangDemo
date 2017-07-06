package com.example.administrator.liangcangdemo.common;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //必须调用初始化
        OkGo.getInstance().init(this);
    }
}

package com.android.coolPlay;


import com.android.coolPlay.bean.Constants;
import com.android.coolPlay.component.ApplicationComponent;
import com.android.coolPlay.component.DaggerApplicationComponent;
import com.android.coolPlay.module.ApplicationModule;
import com.android.coolPlay.module.HttpModule;
import com.android.coolPlay.utils.ContextUtils;
import com.xiaomi.ad.AdSdk;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

public class MyApp extends LitePalApplication {

    private ApplicationComponent mApplicationComponent;

    private static MyApp sMyApp;

    public static int width = 0;

    public static int height = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        BGASwipeBackManager.getInstance().init(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
        LitePal.initialize(this);
        width = ContextUtils.getSreenWidth(MyApp.getContext());
        height = ContextUtils.getSreenHeight(MyApp.getContext());
        AdSdk.setDebugOn();
        AdSdk.initialize(this, Constants.XIAOMI_ID);
    }

    public static MyApp getInstance() {
        return sMyApp;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}

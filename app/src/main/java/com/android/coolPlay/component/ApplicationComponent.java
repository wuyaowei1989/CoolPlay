package com.android.coolPlay.component;

import android.content.Context;

import com.android.coolPlay.MyApp;
import com.android.coolPlay.module.ApplicationModule;
import com.android.coolPlay.module.HttpModule;
import com.android.coolPlay.net.JanDanApi;
import com.android.coolPlay.net.NewsApi;

import dagger.Component;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
@Component(modules = {ApplicationModule.class,HttpModule.class})
public interface ApplicationComponent {

    MyApp getApplication();

    NewsApi getNetEaseApi();

    JanDanApi getJanDanApi();

    Context getContext();

}

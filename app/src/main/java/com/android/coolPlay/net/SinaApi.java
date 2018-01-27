package com.android.coolPlay.net;

import com.android.coolPlay.bean.VideoChannelBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/1/27.
 */

public class SinaApi {

    public static SinaApi sInstance;

    private SinaApiService mService;

    public SinaApi(SinaApiService sinaApiService) {
        this.mService = sinaApiService;
    }

    public static SinaApi getInstance(SinaApiService sinaApiService) {
        if (sInstance == null)
            sInstance = new SinaApi(sinaApiService);
        return sInstance;
    }

    /**
     * 获取视频频道列表
     *
     * @return
     */
    public Observable<List<VideoChannelBean>> getVideoChannel(){
        return mService.getVideoChannel(ApiConstants.sSinaApi);
    }
}

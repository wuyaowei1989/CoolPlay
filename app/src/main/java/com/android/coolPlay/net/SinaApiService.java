package com.android.coolPlay.net;

import com.android.coolPlay.bean.VideoChannelBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/1/27.
 */

public interface SinaApiService {

    @GET
    Observable<List<VideoChannelBean>> getVideoChannel(@Url String url);
}

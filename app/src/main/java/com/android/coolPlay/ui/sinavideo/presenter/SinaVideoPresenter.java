package com.android.coolPlay.ui.sinavideo.presenter;

import com.android.coolPlay.bean.VideoChannelBean;
import com.android.coolPlay.net.RxSchedulers;
import com.android.coolPlay.net.SinaApi;
import com.android.coolPlay.ui.base.BasePresenter;
import com.android.coolPlay.ui.sinavideo.contract.SinaVideoContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2018/1/27.
 */

public class SinaVideoPresenter extends BasePresenter<SinaVideoContract.View> implements SinaVideoContract.Presenter {
    private SinaApi mSinaApi;

    @Inject
    SinaVideoPresenter(SinaApi sinaApi) {
        this.mSinaApi = sinaApi;
    }


    @Override
    public void getVideoChannel() {
        mSinaApi.getVideoChannel()
                .compose(RxSchedulers.<List<VideoChannelBean>>applySchedulers())
                .compose(mView.<List<VideoChannelBean>>bindToLife())
                .subscribe(new Observer<List<VideoChannelBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<VideoChannelBean> videoChannelBeans) {
                        mView.loadVideoChannel(videoChannelBeans);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

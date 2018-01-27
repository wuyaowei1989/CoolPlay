package com.android.coolPlay.ui.sinavideo.contract;

import com.android.coolPlay.bean.VideoChannelBean;
import com.android.coolPlay.ui.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */

public interface SinaVideoContract {
    interface View extends BaseContract.BaseView {
        void loadVideoChannel(List<VideoChannelBean> channelBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 获取视频频道列表
         */
        void getVideoChannel();
    }
}

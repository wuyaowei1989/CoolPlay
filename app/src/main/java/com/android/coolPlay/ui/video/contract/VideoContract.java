package com.android.coolPlay.ui.video.contract;

import com.android.coolPlay.bean.VideoChannelBean;
import com.android.coolPlay.bean.VideoDetailBean;
import com.android.coolPlay.ui.base.BaseContract;

import java.util.List;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/10 .
 */
public interface VideoContract {

    interface View extends BaseContract.BaseView {

        void loadVideoChannel(List<VideoChannelBean> channelBean);

        void loadVideoDetails(List<VideoDetailBean> detailBean);

        void loadMoreVideoDetails(List<VideoDetailBean> detailBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 获取视频频道列表
         */
        void getVideoChannel();

        /**
         * 获取视频列表
         *
         * @param page     页码
         * @param listType 默认list
         * @param typeId   频道id
         */
        void getVideoDetails(int page, String listType, String typeId);
    }
}

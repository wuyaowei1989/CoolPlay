package com.android.coolPlay.ui.news.contract;

import com.android.coolPlay.bean.Channel;
import com.android.coolPlay.ui.base.BaseContract;

import java.util.List;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/7 .
 */
public interface NewsContract{

    interface View extends BaseContract.BaseView{

        void loadData(List<Channel> channels, List<Channel> otherChannels);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 初始化频道
         */
        void getChannel();

    }

}

package com.android.coolPlay.ui.news.contract;

import com.android.coolPlay.bean.NewsArticleBean;
import com.android.coolPlay.ui.base.BaseContract;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/7 .
 */
public interface ArticleReadContract {

    interface View extends BaseContract.BaseView{

        void loadData(NewsArticleBean articleBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void getData(String aid);

    }

}

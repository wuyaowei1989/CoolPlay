package com.android.coolPlay.ui.news.presenter;

import com.android.coolPlay.bean.NewsArticleBean;
import com.android.coolPlay.net.NewsApi;
import com.android.coolPlay.net.RxSchedulers;
import com.android.coolPlay.ui.base.BasePresenter;
import com.android.coolPlay.ui.news.contract.ArticleReadContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/21 .
 */
public class ArticleReadPresenter extends BasePresenter<ArticleReadContract.View> implements ArticleReadContract.Presenter {
    NewsApi mNewsApi;

    @Inject
    public ArticleReadPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(String aid) {
        mNewsApi.getNewsArticle(aid)
                .compose(RxSchedulers.<NewsArticleBean>applySchedulers())
                .compose(mView.<NewsArticleBean>bindToLife())
                .subscribe(new Observer<NewsArticleBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NewsArticleBean articleBean) {
                        mView.loadData(articleBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}

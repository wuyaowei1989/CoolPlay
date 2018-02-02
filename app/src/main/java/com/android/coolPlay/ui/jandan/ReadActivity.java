package com.android.coolPlay.ui.jandan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.coolPlay.R;
import com.android.coolPlay.bean.Constants;
import com.android.coolPlay.bean.FreshNewsArticleBean;
import com.android.coolPlay.bean.FreshNewsBean;
import com.android.coolPlay.component.ApplicationComponent;
import com.android.coolPlay.net.BaseObserver;
import com.android.coolPlay.net.JanDanApi;
import com.android.coolPlay.net.RxSchedulers;
import com.android.coolPlay.ui.base.BaseActivity;
import com.android.coolPlay.utils.DateUtil;
import com.android.coolPlay.utils.StatusBarUtil;
import com.xiaomi.ad.AdListener;
import com.xiaomi.ad.NativeAdInfoIndex;
import com.xiaomi.ad.NativeAdListener;
import com.xiaomi.ad.adView.StandardNewsFeedAd;
import com.xiaomi.ad.common.pojo.AdError;
import com.xiaomi.ad.common.pojo.AdEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadActivity extends BaseActivity {
    private static final String DATA = "data";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_excerpt)
    TextView mTvExcerpt;
    @BindView(R.id.wv_contnet)
    WebView mWebView;
    @BindView(R.id.progress_wheel)
    ProgressBar progressWheel;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    JanDanApi mJanDanApi;
    FreshNewsBean.PostsBean postsBean;
    FreshNewsArticleBean newsArticleBean;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_comment)
    ImageView mIvComment;
    @BindView(R.id.container)
    FrameLayout mContainer;

    public static final String TAG = "ReadActivity";

    public static void launch(Context context, FreshNewsBean.PostsBean postsBean, View view) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(DATA, postsBean);
        context.startActivity(intent);
//        context.startActivity(intent,
//                ActivityOptions.makeSceneTransitionAnimation((Activity) context, view, "topview").toBundle());
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_read;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        mJanDanApi = appComponent.getJanDanApi();
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        final ViewGroup container = (ViewGroup) mContainer;
        final StandardNewsFeedAd standardNewsFeedAd = new StandardNewsFeedAd(this);
        try {
            standardNewsFeedAd.requestAd(Constants.JD_DETAIL_PID, 1, new NativeAdListener() {
                @Override
                public void onNativeInfoFail(AdError adError) {
                    Log.e(TAG, "onNativeInfoFail e : " + adError);
                }

                @Override
                public void onNativeInfoSuccess(List<NativeAdInfoIndex> list) {
                    NativeAdInfoIndex response = list.get(0);
                    standardNewsFeedAd.buildViewAsync(response, container.getWidth(), new AdListener() {
                        @Override
                        public void onAdError(AdError adError) {
                            Log.e(TAG, "error : remove all views");
                            container.removeAllViews();
                        }

                        @Override
                        public void onAdEvent(AdEvent adEvent) {
                            //目前考虑了３种情况，用户点击信息流广告，用户点击x按钮，以及信息流展示的３种回调，范例如下
                            if (adEvent.mType == AdEvent.TYPE_CLICK) {
                                Log.d(TAG, "ad has been clicked!");
                            } else if (adEvent.mType == AdEvent.TYPE_SKIP) {
                                Log.d(TAG, "x button has been clicked!");
                            } else if (adEvent.mType == AdEvent.TYPE_VIEW) {
                                Log.d(TAG, "ad has been showed!");
                            }
                        }

                        @Override
                        public void onAdLoaded() {

                        }

                        @Override
                        public void onViewCreated(View view) {
                            Log.e(TAG, "onViewCreated");
                            container.removeAllViews();
                            container.addView(view);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        StatusBarUtil.setTranslucentForImageView(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA, getStateView());
        if (getIntent().getExtras() == null) return;
        postsBean = (FreshNewsBean.PostsBean) getIntent().getSerializableExtra(DATA);
        if (postsBean == null) return;

        mTvTitle.setText(postsBean.getTitle());
        mTvAuthor.setText(postsBean.getAuthor().getName()
                + "  "
                + DateUtil.getTimestampString(DateUtil.string2Date(postsBean.getDate(), "yyyy-MM-dd HH:mm:ss")));
        mTvExcerpt.setText(postsBean.getExcerpt());
        showSuccess();
        setWebViewSetting();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {
        getData(postsBean.getId());
    }

    private void setWebViewSetting() {
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("file:///android_asset/jd/post_detail.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getData(postsBean.getId());
            }
        });
    }

    private void getData(int id) {
        mJanDanApi.getFreshNewsArticle(id)
                .compose(RxSchedulers.<FreshNewsArticleBean>applySchedulers())
                .compose(this.<FreshNewsArticleBean>bindToLifecycle())
                .subscribe(new BaseObserver<FreshNewsArticleBean>() {
                    @Override
                    public void onSuccess(final FreshNewsArticleBean articleBean) {
                        if (articleBean == null) {
                            showFaild();
                        } else {
                            newsArticleBean = articleBean;
                            mWebView.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressWheel.setVisibility(View.GONE);
                                    final String content = articleBean.getPost().getContent();
                                    String url = "javascript:show_content(\'" + content + "\')";
                                    mWebView.loadUrl(url);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        showFaild();
                    }
                });
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }


    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_comment:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

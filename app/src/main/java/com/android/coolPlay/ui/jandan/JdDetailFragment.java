package com.android.coolPlay.ui.jandan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.coolPlay.R;
import com.android.coolPlay.bean.Constants;
import com.android.coolPlay.bean.FreshNewsBean;
import com.android.coolPlay.bean.JdDetailBean;
import com.android.coolPlay.component.ApplicationComponent;
import com.android.coolPlay.component.DaggerHttpComponent;
import com.android.coolPlay.ui.base.BaseFragment;
import com.android.coolPlay.widget.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaomi.ad.adView.BannerAd;
import com.xiaomi.ad.common.pojo.AdEvent;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.xiaomi.ad.common.GlobalHolder.getApplicationContext;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
@SuppressLint("ValidFragment")
public class JdDetailFragment extends BaseFragment<JanDanPresenter> implements JanDanContract.View {
    public static final String TYPE = "type";
    private static final String TAG = "JdDetailFragment";

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    private BaseQuickAdapter mAdapter;
    private int pageNum = 1;
    private String type;
    BannerAd mBannerAd;
    private View view_Focus;//顶部banner
    private ViewGroup container;

    public JdDetailFragment(BaseQuickAdapter adapter) {
        this.mAdapter = adapter;
    }

    public static JdDetailFragment newInstance(String type, BaseQuickAdapter baseQuickAdapter) {
        Bundle args = new Bundle();
        args.putCharSequence(TYPE, type);
        JdDetailFragment fragment = new JdDetailFragment(baseQuickAdapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_jd_detail;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                mPresenter.getData(type, pageNum);
                requestAD();

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setPreLoadNumber(1);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getData(type, pageNum);
            }
        }, mRecyclerView);

        view_Focus = getView().inflate(getActivity(), R.layout.ad_banner_headerview, null);
        container = (ViewGroup) view_Focus.findViewById(R.id.container);
        mAdapter.addHeaderView(view_Focus);
        requestAD();
    }

    private void requestAD() {
        mBannerAd = new BannerAd(getApplicationContext(), container, new BannerAd.BannerListener() {
            @Override
            public void onAdEvent(AdEvent adEvent) {
                if (adEvent.mType == AdEvent.TYPE_CLICK) {
                    Log.d(TAG, "ad has been clicked!");
                } else if (adEvent.mType == AdEvent.TYPE_SKIP) {
                    Log.d(TAG, "x button has been clicked!");
                } else if (adEvent.mType == AdEvent.TYPE_VIEW) {
                    Log.d(TAG, "ad has been showed!");
                }
            }
        });
        mBannerAd.show(Constants.JD_BANNER_PID);
    }

    @Override
    public void initData() {
        if (getArguments() == null) return;
        type = getArguments().getString(TYPE);
        mPresenter.getData(type, pageNum);
    }

    @Override
    public void onRetry() {
        initData();
    }

    @Override
    public void loadFreshNews(FreshNewsBean freshNewsBean) {
        if (freshNewsBean == null) {
            mPtrFrameLayout.refreshComplete();
            showFaild();
        } else {
            pageNum++;
            mAdapter.setNewData(freshNewsBean.getPosts());
            mPtrFrameLayout.refreshComplete();
            showSuccess();
        }
    }

    @Override
    public void loadDetailData(String type, JdDetailBean jdDetailBean) {
        if (jdDetailBean == null) {
            mPtrFrameLayout.refreshComplete();
            showFaild();
        } else {
            pageNum++;
            mAdapter.setNewData(jdDetailBean.getComments());
            mPtrFrameLayout.refreshComplete();
            showSuccess();
        }
    }

    @Override
    public void loadMoreFreshNews(FreshNewsBean freshNewsBean) {
        if (freshNewsBean == null) {
            mAdapter.loadMoreFail();
        } else {
            pageNum++;
            mAdapter.addData(freshNewsBean.getPosts());
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreDetailData(String type, JdDetailBean jdDetailBean) {
        if (jdDetailBean == null) {
            mAdapter.loadMoreFail();
        } else {
            pageNum++;
            mAdapter.addData(jdDetailBean.getComments());
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadPopularData(JdDetailBean jdDetailBean) {
        if (jdDetailBean == null) {
            mPtrFrameLayout.refreshComplete();
            showFaild();
        } else {
            pageNum++;
            mAdapter.setNewData(jdDetailBean.getComments());
            mPtrFrameLayout.refreshComplete();
            showSuccess();
        }
    }
}

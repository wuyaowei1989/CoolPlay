package com.android.coolPlay.ui.video;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.android.coolPlay.R;
import com.android.coolPlay.bean.VideoChannelBean;
import com.android.coolPlay.bean.VideoDetailBean;
import com.android.coolPlay.component.ApplicationComponent;
import com.android.coolPlay.component.DaggerHttpComponent;
import com.android.coolPlay.ui.adapter.VideoPagerAdapter;
import com.android.coolPlay.ui.base.BaseFragment;
import com.android.coolPlay.ui.video.contract.VideoContract;
import com.android.coolPlay.ui.video.presenter.VideoPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * desc: 视频页面
 * author: Will .
 * date: 2017/9/2 .
 */
public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {
    private static final String TAG = "VideoFragment";
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private VideoPagerAdapter mVideoPagerAdapter;


    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_video;
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

    }

    @Override
    public void initData() {
        mPresenter.getVideoChannel();
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {
        Log.i(TAG, "loadVideoChannel: " + channelBean.toString());
        mVideoPagerAdapter = new VideoPagerAdapter(getChildFragmentManager(), channelBean.get(0));
        mViewpager.setAdapter(mVideoPagerAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        mTablayout.setupWithViewPager(mViewpager, true);
    }

    @Override
    public void loadMoreVideoDetails(List<VideoDetailBean> detailBean) {

    }

    @Override
    public void loadVideoDetails(List<VideoDetailBean> detailBean) {

    }

}

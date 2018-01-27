package com.android.coolPlay.ui.sinavideo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.coolPlay.R;
import com.android.coolPlay.bean.VideoChannelBean;
import com.android.coolPlay.component.ApplicationComponent;
import com.android.coolPlay.component.DaggerHttpComponent;
import com.android.coolPlay.ui.adapter.VideoPagerAdapter;
import com.android.coolPlay.ui.base.BaseFragment;
import com.android.coolPlay.ui.sinavideo.contract.SinaVideoContract;
import com.android.coolPlay.ui.sinavideo.presenter.SinaVideoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/27.
 */

public class SinaVideoFragment extends BaseFragment<SinaVideoPresenter> implements SinaVideoContract.View {

    private static final String TAG = "SinaVideoFragment";

    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    private VideoPagerAdapter mVideoPagerAdapter;

    public static SinaVideoFragment newInstance() {
        Bundle args = new Bundle();
        SinaVideoFragment fragment = new SinaVideoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {
        Log.i(TAG, "loadVideoChannel: " + channelBean.toString());
        mVideoPagerAdapter = new VideoPagerAdapter(getChildFragmentManager(), channelBean.get(0));
        viewpager.setAdapter(mVideoPagerAdapter);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setCurrentItem(0, false);
        tablayout.setupWithViewPager(viewpager, true);
    }
}

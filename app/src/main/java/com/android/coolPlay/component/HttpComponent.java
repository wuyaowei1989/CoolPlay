package com.android.coolPlay.component;

import com.android.coolPlay.ui.jandan.JdDetailFragment;
import com.android.coolPlay.ui.news.ArticleReadActivity;
import com.android.coolPlay.ui.news.ImageBrowseActivity;
import com.android.coolPlay.ui.news.NewsFragment;
import com.android.coolPlay.ui.sinavideo.SinaVideoFragment;
import com.android.coolPlay.ui.video.DetailFragment;
import com.android.coolPlay.ui.video.VideoFragment;

import dagger.Component;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(VideoFragment videoFragment);

    void inject(DetailFragment detailFragment);

    void inject(JdDetailFragment jdDetailFragment);

    void inject(ImageBrowseActivity imageBrowseActivity);

    void inject( com.android.coolPlay.ui.news.DetailFragment detailFragment);

    void inject(ArticleReadActivity articleReadActivity);

    void inject(NewsFragment newsFragment);

    void inject(SinaVideoFragment sinaVideoFragment);
}

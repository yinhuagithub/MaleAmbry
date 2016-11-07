package com.graduation.yinhua.maleambry.view.activity;

import android.view.View;
import android.widget.ImageView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;
import com.graduation.yinhua.maleambry.view.widgets.ParallaxContainer;

import butterknife.BindView;

/**
 * WelcomeActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.parallax_container)
    ParallaxContainer mParallaxContainer;

    @BindView(R.id.iv_man)
    ImageView mIvMan;

    @Override
    protected int getContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mParallaxContainer.setAnimImageView(mIvMan);
        mIvMan.setVisibility(View.VISIBLE);
        mParallaxContainer.setupChildren(getLayoutInflater(), R.layout.view_intro_1, R.layout.view_intro_2, R.layout.view_intro_3, R.layout.view_intro_4, R.layout.view_intro_5);
    }
}

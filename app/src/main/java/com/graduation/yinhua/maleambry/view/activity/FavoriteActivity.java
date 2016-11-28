package com.graduation.yinhua.maleambry.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.FavoriteFragmentPagerAdapter;
import com.graduation.yinhua.maleambry.adapter.SingleFragmentPagerAdapter;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;

/**
 * FavoriteActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class FavoriteActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.tl_favorite)
    TabLayout mTlFavorite;

    @BindView(R.id.vp_favorite)
    ViewPager mVpFavorite;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mTvTitle.setText(R.string.favorite);

        FavoriteFragmentPagerAdapter mAdapter = new FavoriteFragmentPagerAdapter(getSupportFragmentManager(), FavoriteActivity.this);
        mVpFavorite.setAdapter(mAdapter);
        mTlFavorite.setupWithViewPager(mVpFavorite);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteActivity.this.finish();
            }
        });
    }
}

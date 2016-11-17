package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.BannerAdapter;
import com.graduation.yinhua.maleambry.adapter.HomeAdapter;
import com.graduation.yinhua.maleambry.contract.HomeContract;
import com.graduation.yinhua.maleambry.presenter.HomePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;
import com.graduation.yinhua.maleambry.view.widgets.BannerTimerController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * HomeFragment.java
 * Description:首页
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class HomeFragment extends BaseMVPFragment<HomeContract.View, HomePresenter> implements HomeContract.View {
    private static final String TAG = HomeFragment.class.getSimpleName();

    /**
     * 图片轮播默认的时间间隔
     */
    private static final int DEFAULT_INTERVAL = 3000;

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

    private HomeAdapter mHomeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private BannerTimerController mTimerController = new BannerTimerController(DEFAULT_INTERVAL) {
        @Override
        protected void onTick() {
            mHomeAdapter.toNextBanner();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.home);

        linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.scrollToPosition(0);
        mRvHome.setLayoutManager(linearLayoutManager);
        mHomeAdapter = new HomeAdapter();
        mRvHome.setAdapter(mHomeAdapter);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mTimerController.start();
        linearLayoutManager.scrollToPosition(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimerController.cancel();
    }

}

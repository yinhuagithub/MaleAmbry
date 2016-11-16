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

//    @BindView(R.id.vp_banner)
//    ViewPager mVpBanner;
//
//    @BindView(R.id.ll_dots)
//    LinearLayout mLlDots;

//    private BannerAdapter mAdapter;
//
//    private List<String> mData = new ArrayList<>();
//    private BannerTimerController mTimerController = new BannerTimerController(DEFAULT_INTERVAL) {
//        @Override
//        protected void onTick() {
//            mAdapter.toNextItem(mVpBanner);
//        }
//    };
//
//    private BannerAdapter.OnCurrentItemChangedListener mBannerListener = new BannerAdapter.OnCurrentItemChangedListener() {
//        @Override
//        public void onCurrentItemChanged(int currentItem) {
//            int childCount = mLlDots.getChildCount();
//            for (int index = 0; index < childCount; index++) {
//                ImageView iv = (ImageView)mLlDots.getChildAt(index);
//                if(index == currentItem - 1) {
//                    iv.setEnabled(true);
//                } else {
//                    iv.setEnabled(false);
//                }
//            }
//        }
//    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

//        mData.add("http://image3.chelaile.net.cn/5393b01188a84f2886f498a3fa3ac819");
//        mData.add("http://image3.chelaile.net.cn/2dcbcf8031114632a9c7a654b6a38b75");
//        mData.add("http://image3.chelaile.net.cn/ce9c9c0ca23a4efbba622275a3e8a786");
//        mTvTitle.setText(R.string.home);
//        mAdapter = new BannerAdapter(getContext());
//        mAdapter.setOnCurrentItemChangedListener(mBannerListener);
//        mVpBanner.setAdapter(mAdapter);
//        mAdapter.updateData(mVpBanner, mData);

        mRvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeAdapter mAdapter = new HomeAdapter();
        mRvHome.setAdapter(mAdapter);
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
//        mTimerController.start();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mTimerController.cancel();
    }

}

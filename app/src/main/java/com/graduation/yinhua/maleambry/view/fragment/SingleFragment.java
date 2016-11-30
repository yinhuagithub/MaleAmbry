package com.graduation.yinhua.maleambry.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.SingleFragmentPagerAdapter;
import com.graduation.yinhua.maleambry.contract.SingleContract;
import com.graduation.yinhua.maleambry.presenter.SinglePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import butterknife.BindView;

/**
 * SingleFragment.java
 * Description: 单品页
 *
 * Created by yinhua on 2016/11/19.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleFragment extends BaseMVPFragment<SingleContract.View, SinglePresenter> implements SingleContract.View {

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.tl_single)
    TabLayout mTlSingle;

    @BindView(R.id.vp_single)
    ViewPager mVpSingle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.single);

        SingleFragmentPagerAdapter mAdapter = new SingleFragmentPagerAdapter(getChildFragmentManager(), getContext());
        mVpSingle.setOffscreenPageLimit(4);
        mVpSingle.setAdapter(mAdapter);
        mTlSingle.setupWithViewPager(mVpSingle);
    }

    @Override
    protected SinglePresenter createPresenter() {
        return new SinglePresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }
}

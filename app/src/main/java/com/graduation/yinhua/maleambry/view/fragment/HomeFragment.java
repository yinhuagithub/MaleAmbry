package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.contract.HomeContract;
import com.graduation.yinhua.maleambry.presenter.HomePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import butterknife.BindView;

/**
 * Created by yinhua on 2016/11/9.
 */
public class HomeFragment extends BaseMVPFragment<HomeContract.View, HomePresenter> implements HomeContract.View {

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.home);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }
}

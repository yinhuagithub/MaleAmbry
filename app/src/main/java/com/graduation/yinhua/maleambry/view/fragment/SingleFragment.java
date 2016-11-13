package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.contract.SingleContract;
import com.graduation.yinhua.maleambry.presenter.SinglePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import butterknife.BindView;

/**
 * Created by yinhua on 2016/11/9.
 */
public class SingleFragment extends BaseMVPFragment<SingleContract.View, SinglePresenter> implements SingleContract.View {

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.single);
    }

    @Override
    protected SinglePresenter createPresenter() {
        return new SinglePresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }
}

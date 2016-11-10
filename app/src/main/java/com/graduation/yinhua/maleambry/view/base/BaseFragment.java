package com.graduation.yinhua.maleambry.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * BaseFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/10.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getInflateView(getLayoutId(), inflater, container);

        ButterKnife.bind(this, mRootView);
        initWidgets();
        initEvents();

        return mRootView;
    }

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 获取填充视图
     * @param layoutId
     * @param inflater
     * @param container
     */
    protected void getInflateView(int layoutId, LayoutInflater inflater, @Nullable ViewGroup container) {
        mRootView = inflater.inflate(layoutId, container, false);
    }

    /**
     * 初始化控件
     */
    protected void initWidgets() { }

    /**
     * 初始化事件
     */
    protected void initEvents() { }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mRootView != null) {
            ((ViewGroup)mRootView.getParent()).removeView(mRootView);
        }
    }
}

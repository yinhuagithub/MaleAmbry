package com.graduation.yinhua.maleambry.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * BaseLazyLoaderFragment.java
 * Description:  Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 使用懒加载，避免加载过多还未展示的数据
 *
 * Created by yinhua on 2016/11/10.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public abstract class BaseLazyLoaderFragment extends BaseFragment {
    private static final String TAG = BaseLazyLoaderFragment.class.getSimpleName();

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean mHasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean mIsFragmentVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (mRootView == null) {
            return;
        }
        mHasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            mIsFragmentVisible = true;
            return;
        }
        if (mIsFragmentVisible) {
            onFragmentVisibleChange(false);
            mIsFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mHasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            mIsFragmentVisible = true;
        }
    }

    private void initVariable() {
        mHasCreateView = false;
        mIsFragmentVisible = false;
    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected abstract void onFragmentVisibleChange(boolean isVisible);

}

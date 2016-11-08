package com.graduation.yinhua.maleambry.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.graduation.yinhua.maleambry.presenter.BasePresenter;

/**
 * BaseMVPActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends BaseActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}

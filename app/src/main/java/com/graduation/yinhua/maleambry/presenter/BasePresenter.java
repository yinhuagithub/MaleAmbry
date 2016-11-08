package com.graduation.yinhua.maleambry.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * BasePresenter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if(mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}

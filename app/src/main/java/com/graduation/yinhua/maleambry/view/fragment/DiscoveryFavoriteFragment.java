package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.DiscoveryFavoriteAdapter;
import com.graduation.yinhua.maleambry.adapter.SingleFavoriteAdapter;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.view.base.BaseLazyLoaderFragment;

import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DiscoveryFavoriteFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class DiscoveryFavoriteFragment extends BaseLazyLoaderFragment {

    @BindView(R.id.rv_favorite_discovery)
    RecyclerView mRvFavoriteDiscovery;

    private boolean mLoadingMore = true;
    private boolean mRefreshing = false;
    private DiscoveryFavoriteAdapter mDiscoveryAdapter;

    public static DiscoveryFavoriteFragment newInstance() {
        Bundle bundle = new Bundle();
        DiscoveryFavoriteFragment fragment = new DiscoveryFavoriteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite_discovery;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mRvFavoriteDiscovery.setLayoutManager(new LinearLayoutManager(getContext()));
        mDiscoveryAdapter = new DiscoveryFavoriteAdapter();
        mRvFavoriteDiscovery.setAdapter(mDiscoveryAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(mLoadingMore) {
                loadDiscoveryFavoriteByNet();
            }
        }
    }

    /**
     * 加载单品收藏数据
     */
    private void loadDiscoveryFavoriteByNet() {
        User user = MaleAmbryApp.getUser();
        MaleAmbryRetrofit.getInstance().getFavoriteDiscovery(user.getApp_token(), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Discovery>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Discovery>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            mDiscoveryAdapter.addItems(responseMessage.getResults(), false);
                            mLoadingMore = false;
                        }
                    }
                });
    }
}

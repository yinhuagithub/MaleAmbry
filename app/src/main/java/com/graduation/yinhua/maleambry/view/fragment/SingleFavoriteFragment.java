package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.SingleFavoriteAdapter;
import com.graduation.yinhua.maleambry.model.Match;
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
 * SingleFavoriteFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleFavoriteFragment extends BaseLazyLoaderFragment {

    @BindView(R.id.rv_favorite_single)
    RecyclerView mRvFavoriteSingle;

    @BindView(R.id.srl_favorite_single)
    SwipeRefreshLayout mSrlFavoriteSingle;

    private boolean mRefreshing = true;
    private SingleFavoriteAdapter mSingleAdapter;

    public static SingleFavoriteFragment newInstance() {
        Bundle bundle = new Bundle();
        SingleFavoriteFragment fragment = new SingleFavoriteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite_single;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mRvFavoriteSingle.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mSingleAdapter = new SingleFavoriteAdapter();
        mRvFavoriteSingle.setAdapter(mSingleAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSrlFavoriteSingle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!mRefreshing) {
                    mRefreshing = true;
                    loadSingleFavoriteByNet();
                }
            }
        });
        mSrlFavoriteSingle.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(mRefreshing) {
                loadSingleFavoriteByNet();
            }
        }
    }

    /**
     * 加载单品收藏数据
     */
    private void loadSingleFavoriteByNet() {
        User user = MaleAmbryApp.getUser();
        MaleAmbryRetrofit.getInstance().getFavoriteSingle(user.getApp_token(), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Single>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Single>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            mSingleAdapter.addItems(responseMessage.getResults(), false);
                            mRefreshing = false;
                            if(mSrlFavoriteSingle.isRefreshing()) {
                                mSrlFavoriteSingle.setRefreshing(false);
                            }
                        }
                    }
                });
    }
}

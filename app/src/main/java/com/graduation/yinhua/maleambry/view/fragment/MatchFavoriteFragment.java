package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.MatchFavoriteAdapter;
import com.graduation.yinhua.maleambry.adapter.SingleFavoriteAdapter;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.utils.NetworkUtil;
import com.graduation.yinhua.maleambry.view.base.BaseLazyLoaderFragment;

import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MatchFavoriteFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchFavoriteFragment extends BaseLazyLoaderFragment {

    @BindView(R.id.srl_favorite_match)
    SwipeRefreshLayout mSrlFavoriteMatch;

    @BindView(R.id.rv_favorite_match)
    RecyclerView mRvFavoriteMatch;

    private boolean mRefreshing = true;
    private MatchFavoriteAdapter mMatchAdapter;

    public static MatchFavoriteFragment newInstance() {
        Bundle bundle = new Bundle();
        MatchFavoriteFragment fragment = new MatchFavoriteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite_match;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mRvFavoriteMatch.setLayoutManager(new LinearLayoutManager(getContext()));
        mMatchAdapter = new MatchFavoriteAdapter();
        mRvFavoriteMatch.setAdapter(mMatchAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSrlFavoriteMatch.setColorSchemeResources(R.color.colorAccent);
        mSrlFavoriteMatch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!mRefreshing) {
                    mRefreshing = true;
                    loadSingleFavoriteByNet();
                }
            }
        });
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
        if(!NetworkUtil.checkNetwork(getContext(), mRootView)) {
            if(mSrlFavoriteMatch.isRefreshing()) {
                mSrlFavoriteMatch.setRefreshing(false);
            }
            return;
        }
        User user = MaleAmbryApp.getUser();
        MaleAmbryRetrofit.getInstance().getFavoriteMatch(user.getApp_token(), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Match>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mRootView, R.string.network_anomaly, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Match>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            mMatchAdapter.addItems(responseMessage.getResults(), false);
                            mRefreshing = false;
                            if(mSrlFavoriteMatch.isRefreshing()) {
                                mSrlFavoriteMatch.setRefreshing(false);
                            }
                        }
                    }
                });
    }
}

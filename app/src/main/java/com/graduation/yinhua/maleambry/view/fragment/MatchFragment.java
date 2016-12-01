package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.MatchAdapter;
import com.graduation.yinhua.maleambry.contract.MatchContract;
import com.graduation.yinhua.maleambry.listeners.IStyleChangeListener;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.presenter.MatchPresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.smssdk.contact.m;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MatchFragment.java
 * Description:搭配页
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchFragment extends BaseMVPFragment<MatchContract.View, MatchPresenter> implements MatchContract.View,IStyleChangeListener {
    private static final int DEFAULT_STYLE = 1;

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.srl_match)
    SwipeRefreshLayout mSrlMatch;

    @BindView(R.id.rv_match)
    RecyclerView mRvMatch;

    private int mStyle = DEFAULT_STYLE;
    private int mPage;
    private boolean mLoadingMore = false;
    private MatchAdapter mMatchAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.match);

        mMatchAdapter = new MatchAdapter(MatchFragment.this);

        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mMatchAdapter.getItemViewType(position) == MatchItemType.CONTENT.ordinal() ? 1:mLayoutManager.getSpanCount();
            }
        });
        mRvMatch.setLayoutManager(mLayoutManager);
        mRvMatch.setAdapter(mMatchAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSrlMatch.setColorSchemeResources(R.color.colorAccent);
        mSrlMatch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                fetchMatchByNet(mStyle, mPage);
            }
        });

        mRvMatch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition + 1 == mMatchAdapter.getItemCount()) {
                    if(!mLoadingMore) {
                        mLoadingMore = true;
                        fetchMatchByNet(mStyle, mPage);
                    }
                }
            }
        });
    }

    @Override
    protected MatchPresenter createPresenter() {
        return new MatchPresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(mPage == 0 && !mLoadingMore) {
                fetchMatchByNet(mStyle, mPage);
            }
        }
    }

    private void fetchMatchByNet(int style, int page) {
        MaleAmbryRetrofit.getInstance().getMatch(style, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Match>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Match>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            List<Match> results = responseMessage.getResults();
                            if(results != null && results.size() > 0) {
                                fetchServiceDataSuccess(results);
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void changeStyle(int style) {
        mStyle = style + 1;
        mPage = 0;
        fetchMatchByNet(mStyle, mPage);
    }

    private void fetchServiceDataSuccess(List<Match> results) {
        if(mPage == 0) {
            mMatchAdapter.addItems(results, false);
        } else {
            mMatchAdapter.addItems(results, true);
        }
        mPage++;
        mLoadingMore = false;
        if(mSrlMatch.isRefreshing()) {
            mSrlMatch.setRefreshing(false);
        }
    }
}

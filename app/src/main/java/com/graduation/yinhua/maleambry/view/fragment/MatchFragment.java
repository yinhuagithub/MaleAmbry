package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.MatchAdapter;
import com.graduation.yinhua.maleambry.contract.MatchContract;
import com.graduation.yinhua.maleambry.listeners.IStyleChangeListener;
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

    @BindView(R.id.rv_match)
    RecyclerView mRvMatch;

    private int mStyle = DEFAULT_STYLE;
    private int mPage;
    private boolean mLoadingMore = true;
    private boolean mRefreshing = false;
    MatchAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.match);

        mAdapter = new MatchAdapter(MatchFragment.this);

        final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == MatchItemType.CONTENT.ordinal() ? 1:manager.getSpanCount();
            }
        });
        mRvMatch.setLayoutManager(manager);
        mRvMatch.setAdapter(mAdapter);

    }

    @Override
    protected MatchPresenter createPresenter() {
        return new MatchPresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(mLoadingMore) {
                fetchMatchByNet(1, mPage, true);
            }
        }
    }

    private void fetchMatchByNet(int style, int page, final boolean loadNewData) {
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
                            if(loadNewData) {
                                mAdapter.addItems(responseMessage.getResults(), false);
                            } else {
                                mAdapter.addItems(responseMessage.getResults(), true);
                            }
                            mLoadingMore = false;
                        }
                    }
                });
    }

    @Override
    public void changeStyle(int style) {
        mStyle = style + 1;
        mPage = 0;
        fetchMatchByNet(mStyle, mPage, true);
    }
}

package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.SingleStyleAdapter;
import com.graduation.yinhua.maleambry.listeners.IStyleChangeListener;
import com.graduation.yinhua.maleambry.model.ItemType.SingleItemType;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.utils.NetworkUtil;
import com.graduation.yinhua.maleambry.view.base.BaseLazyLoaderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * SingleStyleFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/19.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleStyleFragment extends BaseLazyLoaderFragment implements IStyleChangeListener{

    private static final String TAG = SingleStyleFragment.class.getSimpleName();
    private static final String ARGS_SINGLE_STYLE_PAGE = "single style page";
    private static final int DEFAULT_STYLE = 101;

    @BindView(R.id.srl_single_style)
    SwipeRefreshLayout mSrlSingleStyle;

    @BindView(R.id.rl_single_style)
    RecyclerView mRvSingleStyle;

    private int mStyle = DEFAULT_STYLE;
    private int mPage;
    private boolean mLoadingMore = false;
    private int mSingleStylePage;
    private SingleStyleAdapter mSingleStyleAdapter;
    private GridLayoutManager mLayoutManager;

    public static SingleStyleFragment newInstance(int singleStylePage) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_SINGLE_STYLE_PAGE, singleStylePage);
        SingleStyleFragment fragment = new SingleStyleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mSingleStylePage = bundle.getInt(ARGS_SINGLE_STYLE_PAGE);
            mStyle = (mSingleStylePage + 1) * 100 + 1;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_single_style;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mSingleStyleAdapter = new SingleStyleAdapter(mSingleStylePage, SingleStyleFragment.this);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSingleStyleAdapter.getItemViewType(position) == SingleItemType.CONTENT.ordinal() ? 1 : mLayoutManager.getSpanCount();
            }
        });
        mRvSingleStyle.setLayoutManager(mLayoutManager);
        mRvSingleStyle.setAdapter(mSingleStyleAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSrlSingleStyle.setColorSchemeResources(R.color.colorAccent);
        mSrlSingleStyle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                fetchSingleByNet();
            }
        });

        mRvSingleStyle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition + 1 == mSingleStyleAdapter.getItemCount()) {
                    if(!mLoadingMore) {
                        mLoadingMore = true;
                        fetchSingleByNet();
                    }
                }
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(mPage == 0 && !mLoadingMore) {
                fetchSingleByNet();
            }
        }
    }

    private void fetchSingleByNet() {
        if(!NetworkUtil.checkNetwork(getContext(), mRootView)) {
            if(mSrlSingleStyle.isRefreshing()) {
                mSrlSingleStyle.setRefreshing(false);
            }
            return;
        }
        MaleAmbryRetrofit.getInstance().getSingle(mStyle, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Single>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mRootView, R.string.network_anomaly, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Single>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            List<Single> results = responseMessage.getResults();
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
        mStyle = (mSingleStylePage + 1) * 100 + (style + 1);
        mPage = 0;
        fetchSingleByNet();
    }

    private void fetchServiceDataSuccess(List<Single> results) {
        if(mPage == 0) {
            mSingleStyleAdapter.addItems(results, false);
        } else {
            mSingleStyleAdapter.addItems(results, true);
        }
        mPage++;
        mLoadingMore = false;
        if(mSrlSingleStyle.isRefreshing()) {
            mSrlSingleStyle.setRefreshing(false);
        }
    }
}

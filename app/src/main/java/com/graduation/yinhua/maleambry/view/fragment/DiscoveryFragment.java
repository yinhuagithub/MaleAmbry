package com.graduation.yinhua.maleambry.view.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.DiscoveryAdapter;
import com.graduation.yinhua.maleambry.contract.DiscoveryContract;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.presenter.DiscoveryPresenter;
import com.graduation.yinhua.maleambry.utils.NetworkUtil;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DiscoveryFragment.java
 * Description:发现页
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class DiscoveryFragment extends BaseMVPFragment<DiscoveryContract.View, DiscoveryPresenter> implements DiscoveryContract.View {

    private static final String TAG = DiscoveryFragment.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.srl_discovery)
    SwipeRefreshLayout mSrlDiscovery;

    @BindView(R.id.rv_discovery)
    RecyclerView mRvDiscovery;

    private int mPage;
    private boolean mLoadingMore = false;
    LinearLayoutManager mLayoutManager;
    DiscoveryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.discovery);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvDiscovery.setLayoutManager(mLayoutManager);
        mAdapter = new DiscoveryAdapter();
        mRvDiscovery.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSrlDiscovery.setColorSchemeResources(R.color.colorAccent);
        mSrlDiscovery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                fetchDiscovery(mPage);
            }
        });

        mRvDiscovery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    boolean refreshing = mSrlDiscovery.isRefreshing();
                    if(refreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }

                    if(!mLoadingMore) {
                        mLoadingMore = true;
                        fetchDiscovery(mPage);
                    }
                }
            }
        });
    }

    @Override
    protected DiscoveryPresenter createPresenter() {
        return new DiscoveryPresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {

            if(mPage == 0 && !mLoadingMore) {
                fetchDiscovery(mPage);
            }
        }
    }

    /**
     * 加载发现数据
     * @param page 页码
     */
    private void fetchDiscovery(final int page) {
        if(!NetworkUtil.checkNetwork(getContext(), mRootView)) {
            if(mSrlDiscovery.isRefreshing()) {
                mSrlDiscovery.setRefreshing(false);
            }
            return;
        }
        MaleAmbryRetrofit.getInstance().getDiscovery(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Discovery>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mRootView, R.string.network_anomaly, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Discovery>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            List<Discovery> results = responseMessage.getResults();
                            if(results != null && results.size() > 0) {
                                fetchServiceDataSuccess(page, results);
                            }
                        } else {
                            mAdapter.setFootHint(R.string.no_more_data);
                        }
                    }
                });
    }

    /**
     * 获取服务器数据成功
     * @param page 页码
     * @param results  获取到的数据
     */
    private void fetchServiceDataSuccess(int page, List<Discovery> results) {
        mAdapter.setFootHint(R.string.loading);
        if(page == 0) {
            mAdapter.addItems(results, false);
        } else {
            mAdapter.addItems(results, true);
        }
        mPage++;
        mLoadingMore = false;
        if(mSrlDiscovery.isRefreshing()) {
            mSrlDiscovery.setRefreshing(false);
        }
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }
}

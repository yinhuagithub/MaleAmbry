package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.HomeAdapter;
import com.graduation.yinhua.maleambry.contract.HomeContract;
import com.graduation.yinhua.maleambry.model.ItemType.HomeItemType;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.presenter.HomePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;
import com.graduation.yinhua.maleambry.view.widgets.BannerTimerController;

import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * HomeFragment.java
 * Description:首页
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class HomeFragment extends BaseMVPFragment<HomeContract.View, HomePresenter> implements HomeContract.View {
    private static final String TAG = HomeFragment.class.getSimpleName();

    /**
     * 图片轮播默认的时间间隔
     */
    private static final int DEFAULT_INTERVAL = 5000;

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.srl_home)
    SwipeRefreshLayout mSrlHome;

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

    private boolean mLoadingMore = false;
    private boolean mLoadingFinish = false;
    private HomeAdapter mHomeAdapter;
    private GridLayoutManager mLayoutManager;
    private BannerTimerController mTimerController = new BannerTimerController(DEFAULT_INTERVAL) {
        @Override
        protected void onTick() {
            mHomeAdapter.toNextBanner();
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getContext(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
            mLoadingMore = false;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.home);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mHomeAdapter.getItemViewType(position) == HomeItemType.SINGLE.ordinal() ? 1 : mLayoutManager.getSpanCount();
            }
        });
        mRvHome.setLayoutManager(mLayoutManager);
        mHomeAdapter = new HomeAdapter();
        mRvHome.setAdapter(mHomeAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSrlHome.setColorSchemeResources(R.color.colorAccent);
        mSrlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLoadingFinish = false;
                fetchRecommandSingleByNet();
            }
        });

        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition + 1 == mHomeAdapter.getItemCount()) {
                    if(!mLoadingMore) {
                        mLoadingMore = true;
                        if(!mLoadingFinish) {
                            fetchRecommandSingleByNet();
                        } else {
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(), 1000);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(!mLoadingMore) {
                fetchRecommandSingleByNet();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimerController.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimerController.cancel();
    }

    /**
     * 获取今日精选信息
     */
    private void fetchRecommandSingleByNet() {
        MaleAmbryRetrofit.getInstance().getRecommandSingle()
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
                            mHomeAdapter.addItems(responseMessage.getResults(), false);
                            mLoadingMore = false;
                            mLoadingFinish = true;
                            if(mSrlHome.isRefreshing()) {
                                mSrlHome.setRefreshing(false);
                            }
                        }
                    }
                });
    }
}

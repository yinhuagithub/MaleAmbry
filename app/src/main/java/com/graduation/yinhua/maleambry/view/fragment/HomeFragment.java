package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.BannerAdapter;
import com.graduation.yinhua.maleambry.adapter.HomeAdapter;
import com.graduation.yinhua.maleambry.contract.HomeContract;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.ItemType.HomeItemType;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.presenter.HomePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;
import com.graduation.yinhua.maleambry.view.widgets.BannerTimerController;

import java.util.ArrayList;
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

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

    private boolean mLoadingMore = true;
    private boolean mRefreshing = false;
    private HomeAdapter mHomeAdapter;
    private GridLayoutManager gridLayoutManager;
    private BannerTimerController mTimerController = new BannerTimerController(DEFAULT_INTERVAL) {
        @Override
        protected void onTick() {
            mHomeAdapter.toNextBanner();
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

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mHomeAdapter.getItemViewType(position) == HomeItemType.SINGLE.ordinal() ? 1 : gridLayoutManager.getSpanCount();
            }
        });
        mRvHome.setLayoutManager(gridLayoutManager);
        mHomeAdapter = new HomeAdapter();
        mRvHome.setAdapter(mHomeAdapter);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {
            if(mLoadingMore) {
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
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Single>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            mHomeAdapter.addItems(responseMessage.getResults(), true);
                            mLoadingMore = false;
                        }
                    }
                });
    }
}

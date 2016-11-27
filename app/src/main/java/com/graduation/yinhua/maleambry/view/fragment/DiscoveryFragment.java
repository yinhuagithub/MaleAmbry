package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.DiscoveryAdapter;
import com.graduation.yinhua.maleambry.contract.DiscoveryContract;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.presenter.DiscoveryPresenter;
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

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.rv_discovery)
    RecyclerView mRvDiscovery;

    private int mPage;
    private boolean mLoadingMore = true;
    private boolean mRefreshing = false;
    DiscoveryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mTvTitle.setText(R.string.discovery);
        mRvDiscovery.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DiscoveryAdapter();
//        List<Discovery> list = new ArrayList<>();
//
//        Discovery item1 = new Discovery();
//        item1.setTitle("多长才能满足你？ | 是时候要有一件薄风衣了");
//        item1.setViewed(51972);
//        list.add(item1);
//
//        Discovery item2 = new Discovery();
//        item2.setTitle("出去浪怎么穿，才不拖女友/基友后腿？");
//        item2.setViewed(47360);
//        list.add(item2);
//
//        mAdapter.addItems(list, true);
        mRvDiscovery.setAdapter(mAdapter);
    }

    @Override
    protected DiscoveryPresenter createPresenter() {
        return new DiscoveryPresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible) {

            if(mLoadingMore) {
                fetchDiscovery();
            } else if(mRefreshing) {

            }
        }
    }

    /**
     * 加载发现数据
     * @param
     */
    private void fetchDiscovery() {
        MaleAmbryRetrofit.getInstance().getDiscovery(mPage)
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
                            mAdapter.addItems(responseMessage.getResults(), true);
                        }
                    }
                });
    }
}

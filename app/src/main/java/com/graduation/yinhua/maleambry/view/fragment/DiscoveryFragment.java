package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.DiscoveryAdapter;
import com.graduation.yinhua.maleambry.contract.DiscoveryContract;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.presenter.DiscoveryPresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * DiscoveryFragment.java
 * Description:发现页
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class DiscoveryFragment extends BaseMVPFragment<DiscoveryContract.View, DiscoveryPresenter> implements DiscoveryContract.View {

    @BindView(R.id.rv_discovery)
    RecyclerView mRvDiscovery;

    DiscoveryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mRvDiscovery.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DiscoveryAdapter();
        List<Discovery> list = new ArrayList<>();

        Discovery item1 = new Discovery();
        item1.setTitle("多长才能满足你？ | 是时候要有一件薄风衣了");
        item1.setViewed(51972);
        list.add(item1);

        Discovery item2 = new Discovery();
        item2.setTitle("出去浪怎么穿，才不拖女友/基友后腿？");
        item2.setViewed(47360);
        list.add(item2);

        mAdapter.addItems(list, true);
        mRvDiscovery.setAdapter(mAdapter);
    }

    @Override
    protected DiscoveryPresenter createPresenter() {
        return new DiscoveryPresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }
}

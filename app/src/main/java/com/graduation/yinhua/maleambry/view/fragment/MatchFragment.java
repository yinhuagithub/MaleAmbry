package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.MatchAdapter;
import com.graduation.yinhua.maleambry.contract.MatchContract;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.presenter.MatchPresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MatchFragment.java
 * Description:搭配页
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchFragment extends BaseMVPFragment<MatchContract.View, MatchPresenter> implements MatchContract.View {

    @BindView(R.id.rv_match)
    RecyclerView mRvMatch;

    MatchAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return manager.getSpanCount();
//            }
//        });
        mRvMatch.setLayoutManager(manager);
        mAdapter = new MatchAdapter();

        List list = new ArrayList();
        for (int i = 0; i < 8; i++) {
            Match item = new Match();
            item.setName(getResources().getString(Match.names[i]));
            item.setResource(Match.res[i]);
            list.add(item);
        }
        mAdapter.addItems(list,true);
        mRvMatch.setAdapter(mAdapter);
    }

    @Override
    protected MatchPresenter createPresenter() {
        return new MatchPresenter();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
    }
}

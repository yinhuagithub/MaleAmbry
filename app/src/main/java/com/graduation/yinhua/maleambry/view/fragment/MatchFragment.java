package com.graduation.yinhua.maleambry.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.MatchAdapter;
import com.graduation.yinhua.maleambry.contract.MatchContract;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
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

        List list = new ArrayList();

        Match item1 = new Match();
        item1.setResource(R.mipmap.man1);
        item1.setName("英伦简约搭");
        item1.setDescrption("毛衣+牛仔裤+马丁靴");
        list.add(item1);

        Match item2 = new Match();
        item2.setResource(R.mipmap.man2);
        item2.setName("日韩街头男孩穿搭");
        item2.setDescrption("风衣+休闲裤+高帮鞋");
        list.add(item2);

        Match item3 = new Match();
        item3.setResource(R.mipmap.man3);
        item3.setName("卡其工装外套穿搭");
        item3.setDescrption("工装外套+牛仔裤+帆布鞋");
        list.add(item3);

        mAdapter = new MatchAdapter();
        mAdapter.addItems(list,true);

        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
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
    }
}

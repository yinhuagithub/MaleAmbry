package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.SingleStyleAdapter;
import com.graduation.yinhua.maleambry.model.ItemType.SingleItemType;
import com.graduation.yinhua.maleambry.view.base.BaseLazyLoaderFragment;

import butterknife.BindView;

/**
 * SingleStyleFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/19.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleStyleFragment extends BaseLazyLoaderFragment {

    private static final String TAG = SingleStyleFragment.class.getSimpleName();
    private static final String ARGS_SINGLE_STYLE_PAGE = "single style page";

    @BindView(R.id.rl_single_style)
    RecyclerView mRlSingleStyle;

    private int mSingleStylePage;
    SingleStyleAdapter mSingleStyleAdapter;

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
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_single_style;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        mSingleStyleAdapter = new SingleStyleAdapter(mSingleStylePage);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSingleStyleAdapter.getItemViewType(position) == SingleItemType.CONTENT.ordinal() ? 1 : gridLayoutManager.getSpanCount();
            }
        });
        mRlSingleStyle.setLayoutManager(gridLayoutManager);
        mRlSingleStyle.setAdapter(mSingleStyleAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }
}

package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.SingleStyleAdapter;
import com.graduation.yinhua.maleambry.model.ItemType.SingleItemType;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.view.base.BaseLazyLoaderFragment;

import java.util.ArrayList;
import java.util.List;

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
    public void onResume() {
        super.onResume();

        List<Single> list = new ArrayList<>();
        Single item1 = new Single();
        item1.setThumb(R.mipmap.man1);
        item1.setTitle("街头欧美搭");
        item1.setPrice(227.00);
        item1.setIsfav(false);
        item1.setFavCount(178);
        list.add(item1);

        Single item2 = new Single();
        item2.setThumb(R.mipmap.man2);
        item2.setTitle("日系棒球领棉帽");
        item2.setPrice(115.00);
        item2.setIsfav(false);
        item2.setFavCount(493);
        list.add(item2);

        Single item3 = new Single();
        item3.setThumb(R.mipmap.man3);
        item3.setTitle("个性立领休闲棉服");
        item3.setPrice(138.00);
        item3.setIsfav(false);
        item3.setFavCount(157);
        list.add(item3);
        mSingleStyleAdapter.addItems(list, true);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }
}

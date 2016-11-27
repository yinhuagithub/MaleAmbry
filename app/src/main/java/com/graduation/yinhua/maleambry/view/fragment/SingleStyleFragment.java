package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.SingleStyleAdapter;
import com.graduation.yinhua.maleambry.listeners.IStyleChangeListener;
import com.graduation.yinhua.maleambry.model.ItemType.SingleItemType;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
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

    @BindView(R.id.rl_single_style)
    RecyclerView mRlSingleStyle;

    private int mStyle = DEFAULT_STYLE;
    private int mPage;
    private boolean mLoadingMore = true;
    private boolean mRefreshing = false;
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

        mSingleStyleAdapter = new SingleStyleAdapter(mSingleStylePage, SingleStyleFragment.this);
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
        if(isVisible) {
            if(mLoadingMore) {
                fetchSingleByNet(true);
            }
        }
    }

    private void fetchSingleByNet(final boolean loadNewData) {
        MaleAmbryRetrofit.getInstance().getSingle(mStyle, mPage)
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
                            if(loadNewData) {
                                mSingleStyleAdapter.addItems(responseMessage.getResults(), false);
                            } else {
                                mSingleStyleAdapter.addItems(responseMessage.getResults(), true);
                            }
                            mLoadingMore = false;
                        }
                    }
                });
    }

    @Override
    public void changeStyle(int style) {
        mStyle = (mSingleStylePage + 1) * 100 + (style + 1);
        mPage = 0;
        fetchSingleByNet(true);
    }
}

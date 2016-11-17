package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.ItemType.HomeItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/16.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class HomeAdapter extends BaseRecyclerAdapter<String, RecyclerView.ViewHolder> {

    private static final int TYPE_COUNT = HomeItemType.values().length;

    private Context mContext;

    /**
     * Banner
     */
    private ViewPager mVpBanner;
    private LinearLayout mLlDots;
    private BannerAdapter mBannerAdapter;
    private BannerAdapter.OnCurrentItemChangedListener mBannerListener = new BannerAdapter.OnCurrentItemChangedListener() {
        @Override
        public void onCurrentItemChanged(int currentItem) {
            int childCount = mLlDots.getChildCount();
            for (int index = 0; index < childCount; index++) {
                ImageView iv = (ImageView)mLlDots.getChildAt(index);
                if(index == currentItem - 1) {
                    iv.setEnabled(true);
                } else {
                    iv.setEnabled(false);
                }
            }
        }
    };

    /**
     * Discovery
     */
    private DiscoveryAdapter mDiscoveryAdapter;

    @Override
    public int getItemCount() {
        return super.getItemCount() + TYPE_COUNT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == HomeItemType.BANNER.ordinal()) {
            return new HomeBannerViewHolder(inflateItemView(parent, R.layout.item_home_banner));
        } else if (viewType == HomeItemType.DISCOVERY.ordinal()) {
            return new HomeDiscoveryViewHolder(inflateItemView(parent, R.layout.item_home_discovery));
        } else if (viewType == HomeItemType.MATCH.ordinal()) {
            return new HomeMatchViewHolder(inflateItemView(parent, R.layout.item_home_match));
        } else {
            return new HomeSingleViewHolder(inflateItemView(parent, R.layout.item_home_single));
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        int itemViewType = getItemViewType(position);
        if (itemViewType == HomeItemType.BANNER.ordinal()) {
            bindDataToBannerView((HomeBannerViewHolder) holder);
        } else if (itemViewType == HomeItemType.DISCOVERY.ordinal()) {
            bindDataToDiscoveryView((HomeDiscoveryViewHolder)holder);
        } else if (itemViewType == HomeItemType.MATCH.ordinal()) {
            bindDataToMatchView((HomeMatchViewHolder)holder);
        } else {
            bindDataToSingleView((HomeSingleViewHolder)holder);
        }
    }

    /**
     * 绑定数据到Banner视图
     * @param item
     */
    private void bindDataToBannerView(HomeBannerViewHolder item) {
        if(mBannerAdapter == null) {
            mVpBanner = item.vp_banner;
            mLlDots = item.ll_dots;
            mBannerAdapter = new BannerAdapter(mContext);
            mBannerAdapter.setOnCurrentItemChangedListener(mBannerListener);
            item.vp_banner.setAdapter(mBannerAdapter);
            mBannerAdapter.updateData(item.vp_banner, fetchBannerByNet());
        }
    }

    /**
     * 绑定数据到Discovery视图
     * @param holder
     */
    private void bindDataToDiscoveryView(HomeDiscoveryViewHolder holder) {
        holder.tv_match_title.setText(R.string.home_discovery_title);
        if(mDiscoveryAdapter == null) {
            mDiscoveryAdapter = new DiscoveryAdapter();
            holder.rv_home_discovery.setAdapter(mDiscoveryAdapter);
            holder.rv_home_discovery.setLayoutManager(new LinearLayoutManager(mContext));
            mDiscoveryAdapter.addItems(fetchDiscoveryByNet(), true);
        }
    }

    /**
     * 绑定数据到Match视图
     * @param holder
     */
    private void bindDataToMatchView(HomeMatchViewHolder holder) {
        holder.tv_match_title.setText(R.string.home_match_title);
    }

    /**
     * 绑定数据到Single视图
     * @param holder
     */
    private void bindDataToSingleView(HomeSingleViewHolder holder) {
        holder.tv_match_title.setText(R.string.home_single_title);
    }

    /**
     * 获取banner数据
     */
    private List<String> fetchBannerByNet() {
        List<String> bannerLists = new ArrayList<>();
        bannerLists.add("http://image3.chelaile.net.cn/5393b01188a84f2886f498a3fa3ac819");
        bannerLists.add("http://image3.chelaile.net.cn/2dcbcf8031114632a9c7a654b6a38b75");
        bannerLists.add("http://image3.chelaile.net.cn/ce9c9c0ca23a4efbba622275a3e8a786");
        return bannerLists;
    }

    /**
     * 获取discovery数据
     */
    private List<Discovery> fetchDiscoveryByNet() {
        List<Discovery> discoveryLists = new ArrayList<>();

        Discovery item1 = new Discovery();
        item1.setTitle("多长才能满足你？ | 是时候要有一件薄风衣了");
        item1.setViewed(51972);
        discoveryLists.add(item1);

        Discovery item2 = new Discovery();
        item2.setTitle("出去浪怎么穿，才不拖女友/基友后腿？");
        item2.setViewed(47360);
        discoveryLists.add(item2);
        return discoveryLists;
    }

    /**
     * 跳转到下一个banner
     */
    public void toNextBanner() {
        if(mBannerAdapter != null && mVpBanner != null) {
            mBannerAdapter.toNextItem(mVpBanner);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HomeItemType.BANNER.ordinal();
        } else if (position == 1) {
            return HomeItemType.DISCOVERY.ordinal();
        } else if (position == 2) {
            return HomeItemType.MATCH.ordinal();
        } else {
            return HomeItemType.SINGLE.ordinal();
        }
    }

    public class HomeBannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_banner)
        ViewPager vp_banner;

        @BindView(R.id.ll_dots)
        LinearLayout ll_dots;

        public HomeBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeDiscoveryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        @BindView(R.id.rv_home_discovery)
        RecyclerView rv_home_discovery;

        public HomeDiscoveryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeMatchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        public HomeMatchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeSingleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        public HomeSingleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

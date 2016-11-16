package com.graduation.yinhua.maleambry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.ItemType.HomeItemType;

import butterknife.ButterKnife;

/**
 * HomeAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/16.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class HomeAdapter extends BaseRecyclerAdapter<Object, RecyclerView.ViewHolder> {

    private static final int TYPE_COUNT = HomeItemType.values().length;

    @Override
    public int getItemCount() {
        return TYPE_COUNT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HomeItemType.BANNER.ordinal()) {
            return new HomeBannerViewHolder(inflateItemView(parent, R.layout.item_home_banner));
        } else if (viewType == HomeItemType.DISCOVERY.ordinal()) {
            return new HomeDiscoveryViewHolder(inflateItemView(parent, R.layout.item_discovery));
        } else if (viewType == HomeItemType.MATCH.ordinal()) {
            return new HomeMatchViewHolder(inflateItemView(parent, R.layout.item_match_content));
        } else {
            return new HomeSingleViewHolder(inflateItemView(parent,R.layout.item_single_content));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
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

        public HomeBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeDiscoveryViewHolder extends RecyclerView.ViewHolder {

        public HomeDiscoveryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeMatchViewHolder extends RecyclerView.ViewHolder {

        public HomeMatchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeSingleViewHolder extends RecyclerView.ViewHolder {

        public HomeSingleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

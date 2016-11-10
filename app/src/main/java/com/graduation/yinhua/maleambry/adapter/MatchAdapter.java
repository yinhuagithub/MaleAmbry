package com.graduation.yinhua.maleambry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MatchAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/10.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchAdapter extends BaseRecyclerAdapter<Match, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == MatchItemType.STYLE.ordinal()) {
            return new MatchStyleViewHolder(inflateItemView(parent, R.layout.item_match_style));
        } else if (viewType == MatchItemType.TITLE.ordinal()) {
            return new MatchTitleViewHolder(inflateItemView(parent, R.layout.item_match_title));
        } else {
            return new MatchContentViewHolder(inflateItemView(parent, R.layout.item_match_content));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(position ==  0) {
            type = MatchItemType.STYLE.ordinal();
        } else if (position == 1) {
            type = MatchItemType.TITLE.ordinal();
        } else {
            type = MatchItemType.CONTENT.ordinal();
        }
        return type;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        int itemViewType = getItemViewType(position);

        if(itemViewType == MatchItemType.STYLE.ordinal()) {
            MatchStyleViewHolder styleHolder = (MatchStyleViewHolder) holder;
//            styleHolder.iv_match_style.setImageResource(item.getResource(position));
//            styleHolder.tv_match_style.setText(item.getName());
        } else if (itemViewType == MatchItemType.TITLE.ordinal()) {
            MatchTitleViewHolder titleHolder = (MatchTitleViewHolder) holder;
            titleHolder.tv_match_title.setText("精品搭配");
        } else {
            Match item = getItem(position);
            MatchContentViewHolder contentHolder = (MatchContentViewHolder) holder;
        }
    }

    public class MatchStyleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_match_style)
        RatioImageView iv_match_style;

        @BindView(R.id.tv_match_style)
        TextView tv_match_style;

        public MatchStyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MatchTitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        public MatchTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MatchContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_match_item)
        RatioImageView riv_match_item;

        @BindView(R.id.tv_match_name)
        TextView tv_match_name;

        @BindView(R.id.tv_match_description)
        TextView tv_match_description;

        public MatchContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

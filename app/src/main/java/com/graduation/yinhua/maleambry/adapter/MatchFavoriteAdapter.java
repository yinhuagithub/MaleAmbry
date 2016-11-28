package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MatchFavoriteAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchFavoriteAdapter extends BaseRecyclerAdapter<Match, MatchFavoriteAdapter.MatchViewHolder> {
    private Context mContext;

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MatchViewHolder(inflateItemView(parent, R.layout.item_match_content));
    }

    @Override
    protected void bindDataToItemView(MatchViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        Match item = getItem(position);
        holder.tv_match_title.setText(item.getTitle());
        holder.tv_match_description.setText(item.getDescription());
        Picasso.with(mContext).load(item.getThumb_url()).into(holder.riv_match_item1);
        Picasso.with(mContext).load(item.getThumb2()).into(holder.riv_match_item2);
        Picasso.with(mContext).load(item.getThumb3()).into(holder.riv_match_item3);
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        @BindView(R.id.riv_match_item1)
        RatioImageView riv_match_item1;

        @BindView(R.id.riv_match_item2)
        RatioImageView riv_match_item2;

        @BindView(R.id.riv_match_item3)
        RatioImageView riv_match_item3;

        @BindView(R.id.tv_match_description)
        TextView tv_match_description;

        public MatchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

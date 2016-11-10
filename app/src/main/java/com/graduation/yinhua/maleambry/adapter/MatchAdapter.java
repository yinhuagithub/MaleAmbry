package com.graduation.yinhua.maleambry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
import com.graduation.yinhua.maleambry.model.Match;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MatchAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/10.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchAdapter extends BaseRecyclerAdapter<Match, MatchAdapter.MatchViewHolder> {

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MatchViewHolder(inflateItemView(parent, R.layout.item_match_style));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
//        return super.getItemCount() + 2;
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
    protected void bindDataToItemView(MatchViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        Match item = getItem(position);
        holder.iv_match_style.setImageResource(item.getResource(position));
        holder.tv_match_style.setText(item.getName());
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_match_style)
        ImageView iv_match_style;

        @BindView(R.id.tv_match_style)
        TextView tv_match_style;

        public MatchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

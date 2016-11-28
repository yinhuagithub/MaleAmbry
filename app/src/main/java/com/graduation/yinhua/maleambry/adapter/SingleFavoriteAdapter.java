package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SingleFavoriteAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleFavoriteAdapter extends BaseRecyclerAdapter<Single, SingleFavoriteAdapter.SingleViewHolder> {
    private Context mContext;

    @Override
    public SingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new SingleViewHolder(inflateItemView(parent, R.layout.item_single_content));
    }

    @Override
    protected void bindDataToItemView(SingleViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        Single item = getItem(position);
        Picasso.with(mContext).load(item.getThumb_url()).into(holder.riv_single_item);
        holder.tv_single_name.setText(item.getTitle());
        holder.tv_single_price.setText("" + item.getPrice());
        holder.tv_single_fav_count.setText("" + item.getFavorite_count());
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_single_item)
        RatioImageView riv_single_item;

        @BindView(R.id.tv_single_name)
        TextView tv_single_name;

        @BindView(R.id.tv_single_price)
        TextView tv_single_price;

        @BindView(R.id.iv_single_fav)
        ImageView iv_single_fav;

        @BindView(R.id.tv_single_fav_count)
        TextView tv_single_fav_count;

        public SingleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

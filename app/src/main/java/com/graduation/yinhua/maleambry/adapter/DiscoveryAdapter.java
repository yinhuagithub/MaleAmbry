package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DiscoveryAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/11.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class DiscoveryAdapter extends BaseRecyclerAdapter<Discovery, DiscoveryAdapter.DiscoveryViewHolder> {
    private Context mContext;

    @Override
    public DiscoveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new DiscoveryViewHolder(inflateItemView(parent, R.layout.item_discovery));
    }

    @Override
    protected void bindDataToItemView(DiscoveryViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        Discovery item = getItem(position);

        holder.tv_discovery_title.setText(item.getTitle());
        Picasso.with(mContext).load(item.getThumb()).into(holder.riv_discovery_thumb);
//        holder.riv_discovery_thumb.setImageResource(position == 0 ? R.drawable.discovery1 : R.drawable.discovery2);
        holder.tv_discovery_viewed.setText("" + item.getViewed());
    }

    public class DiscoveryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_discovery_title)
        TextView tv_discovery_title;

        @BindView(R.id.riv_discovery_thumb)
        RatioImageView riv_discovery_thumb;

        @BindView(R.id.tv_discovery_viewed)
        TextView tv_discovery_viewed;

        public DiscoveryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

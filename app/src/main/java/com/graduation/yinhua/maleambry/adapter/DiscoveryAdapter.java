package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.listeners.OnItemClickListener;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.ItemType.DiscoveryItemType;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
import com.graduation.yinhua.maleambry.view.activity.DetailActivity;
import com.graduation.yinhua.maleambry.view.activity.GalleryActivity;
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
public class DiscoveryAdapter extends BaseRecyclerAdapter<Discovery, RecyclerView.ViewHolder> {
    private static final int TYPE_COUNT = DiscoveryItemType.values().length;

    private Context mContext;
    private TextView mFootView;
    private int mFootHint = R.string.loading;

    public void setFootHint(int res) {
        mFootView.setText(res);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + TYPE_COUNT - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount()) {
            return DiscoveryItemType.FOOT.ordinal();
        } else {
            return DiscoveryItemType.CONTENT.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if(viewType == DiscoveryItemType.CONTENT.ordinal()) {
            return new DiscoveryViewHolder(inflateItemView(parent, R.layout.item_discovery));
        } else {
            return new FootViewHolder(inflateItemView(parent, R.layout.item_foot));
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        int itemViewType = getItemViewType(position);
        if(itemViewType == DiscoveryItemType.CONTENT.ordinal()) {
            Discovery item = getItem(position);
            DiscoveryViewHolder discoveryHolder = (DiscoveryViewHolder) holder;

            discoveryHolder.tv_discovery_title.setText(item.getTitle());
            Picasso.with(mContext).load(item.getThumb()).into(discoveryHolder.riv_discovery_thumb);
            discoveryHolder.tv_discovery_viewed.setText("" + item.getViewed());
        } else {
            FootViewHolder footHolder = (FootViewHolder) holder;
            footHolder.tv_foot.setText(mFootHint);
            mFootView = footHolder.tv_foot;
        }
    }

    @Override
    protected void bindListener(RecyclerView.ViewHolder holder, int position) {
        super.bindListener(holder, position);
        int itemViewType = getItemViewType(position);

        if(itemViewType == DiscoveryItemType.CONTENT.ordinal()) {
            setOnItemClickListener(new OnItemClickListener<Discovery>() {
                @Override
                public void onClick(int position, Discovery item) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("thumb_url", item.getDetail_url());
                    mContext.startActivity(intent);
                }
            });
        }
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

    public class FootViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_foot)
        TextView tv_foot;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.MatchStyle;
import com.graduation.yinhua.maleambry.utils.MatchStyleUtil;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;

import java.util.List;

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

    private Context mContext;
    private MatchStyleAdapter mMatchStyleAdapter;
    private TextView tv_title;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
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

            if(mMatchStyleAdapter == null) {
                mMatchStyleAdapter = new MatchStyleAdapter();
                List<MatchStyle> matchStyles = MatchStyleUtil.loadLocalMatchStyleData(mContext);
                mMatchStyleAdapter.addItems(matchStyles, true);
                styleHolder.rv_match_style.setAdapter(mMatchStyleAdapter);
            }

        } else if (itemViewType == MatchItemType.TITLE.ordinal()) {
            MatchTitleViewHolder titleHolder = (MatchTitleViewHolder) holder;
            if(tv_title == null) {
                tv_title = titleHolder.tv_match_title;
            }
            titleHolder.tv_match_title.setText("精品搭配");
        } else {
            Match item = getItem(position - 2);
            MatchContentViewHolder contentHolder = (MatchContentViewHolder) holder;
            contentHolder.riv_match_item.setImageResource(item.getResource());
            contentHolder.tv_match_name.setText(item.getName());
            contentHolder.tv_match_description.setText(item.getDescrption());
        }
    }

    @Override
    protected void bindListener(RecyclerView.ViewHolder holder, int position) {
        super.bindListener(holder, position);

        int itemViewType = getItemViewType(position);

        if(itemViewType == MatchItemType.STYLE.ordinal() && mMatchStyleAdapter != null) {
            mMatchStyleAdapter.addOnItemClickListener(new MatchStyleAdapter.OnMatchStyleSelectedListener() {
                @Override
                public void onSelected(String name) {
                    tv_title.setText(name);
                }
            });
        }
    }

    public class MatchStyleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_match_style)
        RecyclerView rv_match_style;

        public MatchStyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rv_match_style.setLayoutManager(new GridLayoutManager(mContext, 4));
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

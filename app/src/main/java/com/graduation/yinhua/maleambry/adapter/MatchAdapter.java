package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

    private static final String TAG = MatchStyleAdapter.class.getSimpleName();
    private static final int TYPE_COUNT = MatchItemType.values().length;
    private Context mContext;
    private MatchStyleAdapter mMatchStyleAdapter;
    private TextView tv_title;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if(viewType == MatchItemType.STYLE.ordinal()) {
            return new MatchStyleViewHolder(inflateItemView(parent, R.layout.item_match_style));
        } else {
            return new MatchContentViewHolder(inflateItemView(parent, R.layout.item_match_content));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + TYPE_COUNT - 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(position ==  0) {
            type = MatchItemType.STYLE.ordinal();
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

            if(tv_title == null) {
                tv_title = styleHolder.tv_match_title;
            }
            styleHolder.tv_match_title.setText("精品搭配");
        } else {
            Match item = getItem(position - TYPE_COUNT + 1);
            MatchContentViewHolder contentHolder = (MatchContentViewHolder) holder;
            contentHolder.tv_match_title.setText(item.getTitle());
            contentHolder.riv_match_item1.setImageResource(item.getThumb1());
            contentHolder.riv_match_item2.setImageResource(item.getThumb2());
            contentHolder.riv_match_item3.setImageResource(item.getThumb3());
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

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        public MatchStyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rv_match_style.setLayoutManager(new GridLayoutManager(mContext, 4));
        }
    }

    public class MatchContentViewHolder extends RecyclerView.ViewHolder {

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

        public MatchContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

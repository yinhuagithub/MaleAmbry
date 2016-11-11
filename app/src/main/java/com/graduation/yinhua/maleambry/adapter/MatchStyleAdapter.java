package com.graduation.yinhua.maleambry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.listeners.OnItemClickListener;
import com.graduation.yinhua.maleambry.model.MatchStyle;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MatchStyleAdapter.java
 * Description:
 * <p>
 * Created by yinhua on 2016/11/11.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchStyleAdapter extends BaseRecyclerAdapter<MatchStyle, MatchStyleAdapter.MatchStyleViewHolder> {

    private OnMatchStyleSelectedListener onMatchStyleSelectedListener;

    @Override
    public MatchStyleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MatchStyleViewHolder(inflateItemView(parent, R.layout.item_style));
    }

    @Override
    protected void bindDataToItemView(MatchStyleViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        MatchStyle item = getItem(position);
        holder.riv_match_style.setImageResource(item.getRes());
        holder.tv_match_style.setText(item.getStyleName());
    }

    @Override
    protected void bindListener(MatchStyleViewHolder holder, int position) {
        super.bindListener(holder, position);

        setOnItemClickListener(new OnItemClickListener<MatchStyle>() {
            @Override
            public void onClick(MatchStyle item) {
                if(onMatchStyleSelectedListener != null) {
                    onMatchStyleSelectedListener.onSelected(item.getStyleName());
                }
            }
        });
    }

    public interface OnMatchStyleSelectedListener{
        void onSelected(String name);
    }

    public void addOnItemClickListener(OnMatchStyleSelectedListener listener) {
        this.onMatchStyleSelectedListener = listener;
    }

    public class MatchStyleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_match_style)
        RatioImageView riv_match_style;

        @BindView(R.id.tv_match_style)
        TextView tv_match_style;

        public MatchStyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.ItemType.MatchItemType;
import com.graduation.yinhua.maleambry.model.ItemType.SingleItemType;
import com.graduation.yinhua.maleambry.model.MatchStyle;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.utils.MatchStyleUtil;
import com.graduation.yinhua.maleambry.view.fragment.SingleStyleFragment;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SingleStyleAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/20.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleStyleAdapter extends BaseRecyclerAdapter<Single, RecyclerView.ViewHolder> {

    private static final String TAG = SingleStyleFragment.class.getSimpleName();
    private static final String[] TITLE = {"夹克", "休闲裤", "板鞋", "手表"};
    private static final int TYPE_COUNT = SingleItemType.values().length;

    private int mPage;
    private String mTitle;
    private String DEFAULT_TITLE = null;
    private Context mContext;
    private MatchStyleAdapter mMatchStyleAdapter;
    private TextView tv_title;


    public SingleStyleAdapter(int page) {
        this.mPage = page;
        DEFAULT_TITLE = TITLE[page];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if(viewType == SingleItemType.STYLE.ordinal()) {
            return new StyleViewHolder(inflateItemView(parent, R.layout.item_match_style));
        } else {
            return new SingleStyleViewHolder(inflateItemView(parent, R.layout.item_single_content));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + TYPE_COUNT - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return SingleItemType.STYLE.ordinal();
        } else {
            return SingleItemType.CONTENT.ordinal();
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        int itemViewType = getItemViewType(position);
        if(itemViewType == SingleItemType.STYLE.ordinal()) {
            StyleViewHolder styleHolder = (StyleViewHolder) holder;

            if(mMatchStyleAdapter == null) {
                mMatchStyleAdapter = new MatchStyleAdapter();
                List<MatchStyle> matchStyles = MatchStyleUtil.loadLocalSingleStyleData(mContext, mPage);
                mMatchStyleAdapter.addItems(matchStyles, true);
                styleHolder.rv_match_style.setAdapter(mMatchStyleAdapter);
            }

            if(tv_title == null) {
                tv_title = styleHolder.tv_match_title;
            }
            setTitle(DEFAULT_TITLE);
        } else {
            SingleStyleViewHolder singleStyleHolder = (SingleStyleViewHolder) holder;

            Single item = getItem(position - TYPE_COUNT + 1);
            Picasso.with(mContext).load(item.getThumb_url()).into(singleStyleHolder.riv_single_item);
            singleStyleHolder.tv_single_name.setText(item.getTitle());
            singleStyleHolder.tv_single_fav_count.setText("" + item.getFavorite_count());
            singleStyleHolder.tv_single_price.setText("" + item.getPrice());
        }
    }

    /**
     * 设置风格标题
     * @param title
     */
    private void setTitle(String title) {
        if(this.mTitle != null) {
            tv_title.setText(this.mTitle);
        } else {
            tv_title.setText(title);
        }
    }

    @Override
    protected void bindListener(RecyclerView.ViewHolder holder, int position) {
        super.bindListener(holder, position);
        int itemViewType = getItemViewType(position);

        if(itemViewType == SingleItemType.STYLE.ordinal() && mMatchStyleAdapter != null) {
            mMatchStyleAdapter.addOnItemClickListener(new MatchStyleAdapter.OnMatchStyleSelectedListener() {
                @Override
                public void onSelected(String name) {
                    SingleStyleAdapter.this.mTitle = name;
                    setTitle(name);
                }
            });
        }
    }

    public class StyleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_match_style)
        RecyclerView rv_match_style;

        @BindView(R.id.tv_match_title)
        TextView tv_match_title;

        public StyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rv_match_style.setLayoutManager(new GridLayoutManager(mContext, 4));
        }
    }

    public class SingleStyleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_single_item)
        RatioImageView riv_single_item;

        @BindView(R.id.tv_single_name)
        TextView tv_single_name;

        @BindView(R.id.tv_single_fav_count)
        TextView tv_single_fav_count;

        @BindView(R.id.iv_single_fav)
        ImageView iv_single_fav;

        @BindView(R.id.tv_single_price)
        TextView tv_single_price;

        public SingleStyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

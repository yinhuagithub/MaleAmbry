package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.listeners.IObtainDataListener;
import com.graduation.yinhua.maleambry.listeners.OnItemClickListener;
import com.graduation.yinhua.maleambry.model.Banner;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.FavoSingle;
import com.graduation.yinhua.maleambry.model.ItemType.HomeItemType;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.view.activity.DetailActivity;
import com.graduation.yinhua.maleambry.view.activity.GalleryActivity;
import com.graduation.yinhua.maleambry.view.widgets.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * HomeAdapter.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/16.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class HomeAdapter extends BaseRecyclerAdapter<Single, RecyclerView.ViewHolder> {

    private static final int TYPE_COUNT = HomeItemType.values().length;

    private Context mContext;

    /**
     * Banner
     */
    private ViewPager mVpBanner;
    private LinearLayout mLlDots;
    private BannerAdapter mBannerAdapter;
    private BannerAdapter.OnCurrentItemChangedListener mBannerListener = new BannerAdapter.OnCurrentItemChangedListener() {
        @Override
        public void onCurrentItemChanged(int currentItem) {
            int childCount = mLlDots.getChildCount();
            for (int index = 0; index < childCount; index++) {
                ImageView iv = (ImageView)mLlDots.getChildAt(index);
                if(index == currentItem - 1) {
                    iv.setEnabled(true);
                } else {
                    iv.setEnabled(false);
                }
            }
        }
    };

    @Override
    public int getItemCount() {
        return super.getItemCount() + TYPE_COUNT - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == HomeItemType.BANNER.ordinal()) {
            return new HomeBannerViewHolder(inflateItemView(parent, R.layout.item_home_banner));
        } else if (viewType == HomeItemType.DISCOVERY_WITH_TITLE.ordinal()) {
            return new HomeDiscoveryWithTitleViewHolder(inflateItemView(parent, R.layout.item_home_discovery_with_title));
        } else if (viewType == HomeItemType.DISCOVERY.ordinal()) {
            return new HomeDiscoveryViewHolder(inflateItemView(parent, R.layout.item_discovery));
        } else if (viewType == HomeItemType.MATCH_WITH_TITLE.ordinal()) {
            return new HomeMatchWithTitleViewHolder(inflateItemView(parent, R.layout.item_home_match_with_title));
        } else if (viewType == HomeItemType.MATCH.ordinal()) {
            return new HomeMatchViewHolder(inflateItemView(parent, R.layout.item_match_content));
        } else if (viewType == HomeItemType.SINGLE_TITLE.ordinal()) {
            return new HomeSingleTitleViewHolder(inflateItemView(parent, R.layout.item_match_title));
        }else {
            return new HomeSingleViewHolder(inflateItemView(parent, R.layout.item_single_content));
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder holder, int position) {
        super.bindDataToItemView(holder, position);

        int itemViewType = getItemViewType(position);
        if (itemViewType == HomeItemType.BANNER.ordinal()) {
            bindDataToBannerView((HomeBannerViewHolder) holder);
        } else if (itemViewType == HomeItemType.DISCOVERY_WITH_TITLE.ordinal()) {
            bindDataToDiscoveryWithTitleView((HomeDiscoveryWithTitleViewHolder)holder);
        } else if (itemViewType == HomeItemType.DISCOVERY.ordinal()) {
            bindDataToDiscoveryView((HomeDiscoveryViewHolder)holder);
        } else if (itemViewType == HomeItemType.MATCH_WITH_TITLE.ordinal()) {
            bindDataToMatchWithTitleView((HomeMatchWithTitleViewHolder) holder);
        } else if (itemViewType == HomeItemType.MATCH.ordinal()) {
            bindDataToMatchView((HomeMatchViewHolder)holder);
        } else if (itemViewType == HomeItemType.SINGLE_TITLE.ordinal()) {
            bindDataToSingleTitleView((HomeSingleTitleViewHolder)holder);
        } else {
            bindDataToSingleView((HomeSingleViewHolder)holder, position);
        }
    }

    @Override
    protected void bindListener(RecyclerView.ViewHolder holder, int position) {
        super.bindListener(holder, position);

        int itemViewType = getItemViewType(position);
        if (itemViewType == HomeItemType.SINGLE.ordinal()) {
            HomeSingleViewHolder singleHolder = (HomeSingleViewHolder) holder;
            final Single single = getItem(position - TYPE_COUNT + 1);

            singleHolder.iv_single_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = MaleAmbryApp.getUser();
                    if(MaleAmbryApp.containsSingle(single.getSid())) {
                        FavoSingle.removeFavoSid(user.getApp_token(), single.getSid());
                        MaleAmbryApp.removeSingle(single.getSid());
                        ((ImageView)v).setSelected(false);
                    } else {
                        if(user != null && user.isLogin()) {
                            FavoSingle.addFavoSid(user.getApp_token(), single.getSid());
                            FavoSingle favoSingle = new FavoSingle();
                            favoSingle.setSid(single.getSid());
                            MaleAmbryApp.getmFavoSingleList().add(favoSingle);
                            ((ImageView)v).setSelected(true);
                        } else {
                            Toast.makeText(mContext, "请先登录后，再来收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            setOnItemPositionListener(new OnItemPositionListener() {
                @Override
                public void onPosition(int position) {
                    Intent intent = new Intent(mContext, GalleryActivity.class);
                    intent.putExtra("type", "single");
                    intent.putExtra("title", single.getTitle());
                    intent.putExtra("sid", single.getSid());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 绑定数据到Banner视图
     * @param item
     */
    private void bindDataToBannerView(HomeBannerViewHolder item) {
        if(mBannerAdapter == null) {
            mVpBanner = item.vp_banner;
            mLlDots = item.ll_dots;
            mBannerAdapter = new BannerAdapter(mContext);
            mBannerAdapter.setOnCurrentItemChangedListener(mBannerListener);
            item.vp_banner.setAdapter(mBannerAdapter);
            fetchBannerByNet(item.vp_banner);
        }
    }

    /**
     * 绑定数据到HomeDiscoveryWithTitle视图
     * @param holder
     */
    private void bindDataToDiscoveryWithTitleView(final HomeDiscoveryWithTitleViewHolder holder) {
        holder.tv_title.setText(R.string.home_discovery_title);
        fetchDiscoveryByNet(0, new IObtainDataListener<Discovery>() {
            @Override
            public void obtainData(final Discovery discovery) {
                holder.tv_discovery_title.setText(discovery.getTitle());
                Picasso.with(mContext).load(discovery.getThumb()).into(holder.riv_discovery_thumb);
                holder.tv_discovery_viewed.setText("" + discovery.getViewed());
                holder.item_title_discovery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("title", discovery.getTitle());
                        intent.putExtra("thumb_url", discovery.getDetail_url());
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 绑定数据到Discovery视图
     * @param holder
     */
    private void bindDataToDiscoveryView(final HomeDiscoveryViewHolder holder) {
        fetchDiscoveryByNet(1, new IObtainDataListener<Discovery>() {
            @Override
            public void obtainData(final Discovery discovery) {
                holder.tv_discovery_title.setText(discovery.getTitle());
                Picasso.with(mContext).load(discovery.getThumb()).into(holder.riv_discovery_thumb);
                holder.tv_discovery_viewed.setText("" + discovery.getViewed());
                holder.rl_discovery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("title", discovery.getTitle());
                        intent.putExtra("thumb_url", discovery.getDetail_url());
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 绑定数据到MatchWithTitle视图
     * @param holder
     */
    private void bindDataToMatchWithTitleView(final HomeMatchWithTitleViewHolder holder) {
        holder.tv_title.setText(R.string.home_match_title);
        fetchMatchByNet(0, new IObtainDataListener<Match>() {
            @Override
            public void obtainData(final Match match) {
                holder.tv_match_title.setText(match.getTitle());
                Picasso.with(mContext).load(match.getThumb_url()).into(holder.riv_match_item1);
                Picasso.with(mContext).load(match.getThumb1()).into(holder.riv_match_item2);
                Picasso.with(mContext).load(match.getThumb2()).into(holder.riv_match_item3);
                holder.tv_match_description.setText(match.getDescription());
                holder.item_title_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GalleryActivity.class);
                        intent.putExtra("type", "match");
                        intent.putExtra("title", match.getTitle());
                        intent.putExtra("mid", match.getMid());
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }
    /**
     * 绑定数据到Match视图
     * @param holder
     */
    private void bindDataToMatchView(final HomeMatchViewHolder holder) {
        fetchMatchByNet(1, new IObtainDataListener<Match>() {
            @Override
            public void obtainData(final Match match) {
                holder.tv_match_title.setText(match.getTitle());
                Picasso.with(mContext).load(match.getThumb_url()).into(holder.riv_match_item1);
                Picasso.with(mContext).load(match.getThumb1()).into(holder.riv_match_item2);
                Picasso.with(mContext).load(match.getThumb2()).into(holder.riv_match_item3);
                holder.tv_match_description.setText(match.getDescription());
                holder.rl_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GalleryActivity.class);
                        intent.putExtra("type", "match");
                        intent.putExtra("title", match.getTitle());
                        intent.putExtra("mid", match.getMid());
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 绑定数据到SingleTitle视图
     * @param holder
     */
    private void bindDataToSingleTitleView(HomeSingleTitleViewHolder holder) {
        holder.tv_title.setText(R.string.home_single_title);
    }

    /**
     * 绑定数据到Single视图
     * @param holder
     */
    private void bindDataToSingleView(HomeSingleViewHolder holder, int position) {
        Single single = getItem(position - TYPE_COUNT + 1);
        Picasso.with(mContext).load(single.getThumb_url()).into(holder.riv_single_item);
        holder.tv_single_name.setText(single.getTitle());
        holder.tv_single_price.setText("" + single.getPrice());
        holder.tv_single_fav_count.setText("" + single.getFavorite_count());
        if(MaleAmbryApp.containsSingle(single.getSid())) {
            holder.iv_single_fav.setSelected(true);
        } else {
            holder.iv_single_fav.setSelected(false);
        }
    }

    /**
     * 获取banner数据
     */
    private synchronized void fetchBannerByNet(final ViewPager viewPager) {
        MaleAmbryRetrofit.getInstance().getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<Banner>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MaleAmbryApp.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseMessage<List<Banner>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            mBannerAdapter.updateData(viewPager, responseMessage.getResults());
                        }
                    }
                });
    }

    /**
     * 跳转到下一个banner
     */
    public void toNextBanner() {
        if(mBannerAdapter != null && mVpBanner != null) {
            mBannerAdapter.toNextItem(mVpBanner);
        }
    }

    /**
     * 获取discovery数据
     */
    private synchronized void fetchDiscoveryByNet(int page, final IObtainDataListener<Discovery> listener) {
        MaleAmbryRetrofit.getInstance().getRecommandDiscovery(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<Discovery>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MaleAmbryApp.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseMessage<Discovery> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            if(listener != null) {
                                listener.obtainData(responseMessage.getResults());
                            }
                        }
                    }
                });
    }

    /**
     * 获取discovery数据
     */
    private void fetchMatchByNet(int page, final IObtainDataListener<Match> listener) {
        MaleAmbryRetrofit.getInstance().getRecommandMatch(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<Match>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MaleAmbryApp.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseMessage<Match> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            if(listener != null) {
                                listener.obtainData(responseMessage.getResults());
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HomeItemType.BANNER.ordinal();
        } else if (position == 1) {
            return HomeItemType.DISCOVERY_WITH_TITLE.ordinal();
        } else if (position == 2) {
            return HomeItemType.DISCOVERY.ordinal();
        } else if (position == 3) {
            return HomeItemType.MATCH_WITH_TITLE.ordinal();
        } else if (position == 4) {
            return HomeItemType.MATCH.ordinal();
        } else if (position == 5) {
            return HomeItemType.SINGLE_TITLE.ordinal();
        }  else {
            return HomeItemType.SINGLE.ordinal();
        }
    }

    public class HomeBannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_banner)
        ViewPager vp_banner;

        @BindView(R.id.ll_dots)
        LinearLayout ll_dots;

        public HomeBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeDiscoveryWithTitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title_discovery)
        RelativeLayout item_title_discovery;

        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_discovery_title)
        TextView tv_discovery_title;

        @BindView(R.id.riv_discovery_thumb)
        RatioImageView riv_discovery_thumb;

        @BindView(R.id.tv_discovery_viewed)
        TextView tv_discovery_viewed;

        public HomeDiscoveryWithTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeDiscoveryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_discovery)
        RelativeLayout rl_discovery;

        @BindView(R.id.tv_discovery_title)
        TextView tv_discovery_title;

        @BindView(R.id.riv_discovery_thumb)
        RatioImageView riv_discovery_thumb;

        @BindView(R.id.tv_discovery_viewed)
        TextView tv_discovery_viewed;

        public HomeDiscoveryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeMatchWithTitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title_match)
        RelativeLayout item_title_match;

        @BindView(R.id.tv_title)
        TextView tv_title;

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

        public HomeMatchWithTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeMatchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_match)
        RelativeLayout rl_match;

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

        public HomeMatchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeSingleTitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;

        public HomeSingleTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeSingleViewHolder extends RecyclerView.ViewHolder {

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

        public HomeSingleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

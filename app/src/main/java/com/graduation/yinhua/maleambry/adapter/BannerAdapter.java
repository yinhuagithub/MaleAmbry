package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.Banner;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * HomeFragment.java
 * Description:轮播广告适配器
 * 通过在左右两边各增加一个伪Pager，滑动到0，和最后一个时，无动画切换到最后一个和0，从而实现循环滑动
 * <p/>
 * Created by yinhua on 2016/11/13.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class BannerAdapter extends PagerAdapter {

    private Context mContext;
//    private List<String> mImageUrlList = new ArrayList<>();
    private List<Banner> mBannerList = new ArrayList<>();
    private LinkedList<ImageView> mImageCacheList = new LinkedList<>();

    private OnCurrentItemChangedListener mOnCurrentItemChangedListener;

    public BannerAdapter(Context context) {
        this.mContext = context;
    }

    public void updateData(final ViewPager viewPager, List<Banner> bannerList) {
        this.mBannerList = bannerList;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();
                if(currentItem >= 1 && currentItem <= getCount() - 1) {
                    if(mOnCurrentItemChangedListener != null) {
                        mOnCurrentItemChangedListener.onCurrentItemChanged(currentItem);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE) {
                    int currentItem = viewPager.getCurrentItem();
                    int pageCount = getCount();
                    if (currentItem == 0) {
                        viewPager.setCurrentItem(pageCount - 2, false);
                    } else if (currentItem == pageCount - 1) {
                        viewPager.setCurrentItem(1, false);
                    }
                }
            }
        });
        notifyDataSetChanged();
    }

    /**
     * 切换到下一个item
     */
    public void toNextItem(ViewPager viewPager) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == 0) {
            viewPager.setCurrentItem(getCount() - 2, false);
        } else if (currentItem == getCount() - 1) {
            viewPager.setCurrentItem(1, false);
        } else {
            viewPager.setCurrentItem(currentItem + 1, true);
        }
    }

    @Override
    public int getCount() {
        return mBannerList.isEmpty() ? 0 : mBannerList.size() + 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realIndex = position - 1;
        if (position == 0) {
            realIndex = mBannerList.size() - 1;
        } else if (position == getCount() - 1) {
            realIndex = 0;
        }
        final String url = MaleAmbryRetrofit.BASE_IMAGE_URL + mBannerList.get(realIndex).getThumbnail();

        ImageView imageView = initImageView(url);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView) object;
        container.removeView(imageView);

        mImageCacheList.push(imageView);
    }

    /**
     * 初始化ImageView
     * @param url
     * @return
     */
    private ImageView initImageView(String url) {
        final ImageView imageView;
        if (mImageCacheList.isEmpty()) {
            imageView = createImageView();
        } else {
            imageView = mImageCacheList.pop();
        }

        Picasso.with(mContext).load(url).placeholder(R.drawable.default_place_holder_banner).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterDetailActivity();
            }
        });

        return imageView;
    }

    /**
     * 进入详细Activity
     */
    private void enterDetailActivity() {

    }

    /**
     * 动态创建ImageView
     * @return
     */
    private ImageView createImageView() {
        ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.banner, null);
        return imageView;
    }

    public void setOnCurrentItemChangedListener(OnCurrentItemChangedListener onCurrentItemChangedListener) {
        mOnCurrentItemChangedListener = onCurrentItemChangedListener;
    }

    /**
     * ViewPager CurrentItem 发生改变时监听
     */
    public interface OnCurrentItemChangedListener {

        void onCurrentItemChanged(int currentItem);
    }
}

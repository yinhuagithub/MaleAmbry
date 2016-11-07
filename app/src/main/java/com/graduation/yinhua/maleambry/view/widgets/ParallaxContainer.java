package com.graduation.yinhua.maleambry.view.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.ParallaxPagerAdapter;
import com.graduation.yinhua.maleambry.model.ParallaxViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ParallaxContainer.java
 * Description: 自定义视差ImageView容器
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ParallaxContainer extends FrameLayout {

    private static final long DELAY_TIME = 600;

    private final Context mContext;
    private final ParallaxPagerAdapter mParallaxPagerAdapter;

    private ViewPager mParallaxViewPager;
    private ViewPager.OnPageChangeListener mPagerChangeListener;

    private ImageView mAnimMan;

    private List<View> parallaxViews = new ArrayList<View>();
    private List<View> rootViews = new ArrayList<View>();
    private boolean isEnd = false;
    private boolean isLooping;
    private int pageCount;
    private int currentPosition;
    private int containerWidth;

    public ParallaxContainer(Context context) {
        this(context, null);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        mParallaxPagerAdapter = new ParallaxPagerAdapter(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        containerWidth = getMeasuredWidth();

        //还原当前页面到初始位置
        if(mParallaxViewPager != null) {
            mPagerChangeListener.onPageScrolled(mParallaxViewPager.getCurrentItem(), 0, 0);
        }

        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 设置图片动画对象
     * @param ivAnimImageView
     */
    public void setAnimImageView(ImageView ivAnimImageView) {
        this.mAnimMan = ivAnimImageView;
        updateAdapterCount();
    }

    /**
     * 更新adapter数量
     */
    private void updateAdapterCount() {
        mParallaxPagerAdapter.setPagerCount(pageCount);
    }

    /**
     * 设置包含的子布局
     * @param inflater
     * @param childIds
     */
    public void setupChildren(LayoutInflater inflater, int... childIds) {
        if(getChildCount() > 0) {
            throw new RuntimeException("setupChildren should only be called when ParallaxContainer is empty");
        }

        for (int childId : childIds) {
            View view = inflater.inflate(childId, this);
            rootViews.add(view);
        }

        pageCount = getChildCount();

        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            View view = getChildAt(pageIndex);
            addParallaxView(view, pageIndex);
        }

        updateAdapterCount();

        initParallaxViewPager();
    }

    /**
     * 初始化视差viewpager
     */
    private void initParallaxViewPager() {
        mParallaxViewPager = new ViewPager(getContext());
        mParallaxViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        attachOnPageChangeListener();
        mParallaxViewPager.setAdapter(mParallaxPagerAdapter);
        addView(mParallaxViewPager, 0);
    }

    /**
     * 绑定page change 监听
     */
    private void attachOnPageChangeListener() {
        mPagerChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                pageScrolled(position, offsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                pageScrollStateChanged(state);
            }
        };
        mParallaxViewPager.addOnPageChangeListener(mPagerChangeListener);
    }

    /**
     * 页面滚动变化
     * @param position
     * @param offsetPixels
     */
    private void pageScrolled(int position, int offsetPixels) {
        if (pageCount > 0) {
            position = position % pageCount;
        }

        if (position == 3) {
            mAnimMan.setX(mAnimMan.getLeft() - offsetPixels);
        }

        ParallaxViewHolder tag;
        for (View view : parallaxViews) {
            tag = (ParallaxViewHolder) view.getTag();
            if (tag == null) {
                continue;
            }

            if ((position == tag.getIndex() - 1) && containerWidth != 0) {
                // slide in from right top alpha
                view.setVisibility(VISIBLE);
                view.setTranslationX((containerWidth - offsetPixels) * tag.getxIn());
                view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.getyIn());
                view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.getAlphaIn() / containerWidth);
            } else if (position == tag.getIndex()) {
                // slide in from left top alpha
                view.setVisibility(VISIBLE);
                view.setTranslationX(0 - offsetPixels * tag.getxOut());
                view.setTranslationY(0 - offsetPixels * tag.getyOut());
                view.setAlpha(1.0f - offsetPixels * tag.getAlphaOut() / containerWidth);
            } else {
                view.setVisibility(GONE);
            }
        }
    }

    /**
     * parallax viewpager页面滚动状态变化
     * @param state
     */
    private void pageScrollStateChanged(int state) {
        mAnimMan.setBackgroundResource(R.drawable.man_run);
        final AnimationDrawable anim = (AnimationDrawable) mAnimMan.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE: {
                finishAnim(anim);
                break;
            }
            case ViewPager.SCROLL_STATE_DRAGGING: {
                isEnd = false;
                anim.start();
                break;
            }
            case ViewPager.SCROLL_STATE_SETTLING: {
                finishAnim(anim);
                break;
            }
        }
    }

    /**
     * 停止动画播放
     * @param anim
     */
    private synchronized void finishAnim(final AnimationDrawable anim) {
        if(isEnd) {
            return;
        }
        isEnd = true;
        new Timer().schedule(getAnimDelayTimeTask(anim), DELAY_TIME);
    }

    /**
     * 获取动画延迟任务
     * @param anim
     * @return
     */
    private TimerTask getAnimDelayTimeTask(final AnimationDrawable anim) {
        return new TimerTask() {
            @Override
            public void run() {
                if(anim.isRunning() && isEnd) {
                    anim.stop();
                }
            }
        };
    }

    /**
     * 添加视差视图
     * @param view
     * @param pageIndex
     */
    private void addParallaxView(View view, int pageIndex) {
        if(view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int childCount = viewGroup.getChildCount();

            for (int childIndex = 0; childIndex < childCount; childIndex++) {
                addParallaxView(viewGroup.getChildAt(childIndex), pageIndex);
            }
        }

        ParallaxViewHolder viewHolder = (ParallaxViewHolder) view.getTag();
        if(viewHolder != null) {
            viewHolder.setIndex(pageIndex);
            parallaxViews.add(view);
        }
    }
}

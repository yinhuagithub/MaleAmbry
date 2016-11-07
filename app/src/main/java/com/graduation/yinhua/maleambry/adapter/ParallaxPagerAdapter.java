package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * ParallaxPagerAdapter.java
 * Description: 自定义视差容器适配器
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ParallaxPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final LinkedList<View> recyclerList = new LinkedList<View>();
    private int pagerCount = 0;

    public ParallaxPagerAdapter(Context context) {
        this.mContext = context;
    }

    public void setPagerCount(int pagerCount) {
        this.pagerCount = pagerCount;
    }

    @Override
    public int getCount() {
        return pagerCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if(!recyclerList.isEmpty()) {
            view = recyclerList.pop();
        } else {
            view = new View(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        recyclerList.push(view);
    }
}

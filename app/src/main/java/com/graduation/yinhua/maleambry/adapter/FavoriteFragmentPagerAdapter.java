package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseFragment;
import com.graduation.yinhua.maleambry.view.fragment.DiscoveryFavoriteFragment;
import com.graduation.yinhua.maleambry.view.fragment.MatchFavoriteFragment;
import com.graduation.yinhua.maleambry.view.fragment.SingleFavoriteFragment;
import com.graduation.yinhua.maleambry.view.fragment.SingleStyleFragment;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * FavoriteFragmentPagerAdapter.java
 * Description: 我的收藏页fragment适配器
 *
 * Created by yinhua on 2016/11/19.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class FavoriteFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int[] TABS = {R.string.single, R.string.match, R.string.discovery};

    private Context mContext;
    private HashMap<Integer, WeakReference<BaseFragment>> mFavoriteMap = new HashMap<>();

    public FavoriteFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        WeakReference<BaseFragment> baseFragmentWeakReference = mFavoriteMap.get(position);
        if (baseFragmentWeakReference != null) {
            baseFragment = baseFragmentWeakReference.get();
        } else {
            switch (position) {
                case 0: {
                    baseFragment = SingleFavoriteFragment.newInstance();
                    break;
                }
                case 1: {
                    baseFragment = MatchFavoriteFragment.newInstance();
                    break;
                }
                case 2: {
                    baseFragment = DiscoveryFavoriteFragment.newInstance();
                    break;
                }
            }
            mFavoriteMap.put(position, new WeakReference<BaseFragment>(baseFragment));
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return TABS == null ? 0 : TABS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TABS[position]);
    }
}

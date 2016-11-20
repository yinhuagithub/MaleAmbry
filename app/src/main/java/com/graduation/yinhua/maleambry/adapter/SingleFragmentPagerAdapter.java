package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.fragment.SingleStyleFragment;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * SingleFragmentPagerAdapter.java
 * Description: 单品页fragment适配器
 *
 * Created by yinhua on 2016/11/19.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SingleFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int[] TABS = {R.string.single_indicator_tops, R.string.single_indicator_pants, R.string.single_indicator_shoes, R.string.single_indicator_accessory};

    private Context mContext;
    private HashMap<Integer, WeakReference<SingleStyleFragment>> mSingleStyleMap = new HashMap<>();

    public SingleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        SingleStyleFragment singleStyleFragment = null;
        WeakReference<SingleStyleFragment> singleStyleFragmentWeakReference = mSingleStyleMap.get(position);
        if (singleStyleFragmentWeakReference != null) {
            singleStyleFragment = singleStyleFragmentWeakReference.get();
        } else {
            singleStyleFragment = SingleStyleFragment.newInstance(position);
            mSingleStyleMap.put(position, new WeakReference<SingleStyleFragment>(singleStyleFragment));
        }
        return singleStyleFragment;
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

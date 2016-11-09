package com.graduation.yinhua.maleambry.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.fragment.OneFragment;

/**
 * MainActivity.java
 * Description: 主界面
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MainActivity extends AppCompatActivity {
    TabLayout mTablayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    private void initViews() {

        mTablayout= (TabLayout) findViewById(R.id.tabLayout);
        mViewPager= (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"首页", "单品", "搭配","发现", "我的"};

            @Override
            public Fragment getItem(int position) {
                return new OneFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });

        mTablayout.setupWithViewPager(mViewPager);

        TabLayout.Tab tab = mTablayout.getTabAt(0);
        tab.setIcon(getResources().getDrawable(R.mipmap.unselected_home));

        tab = mTablayout.getTabAt(1);
        tab.setIcon(getResources().getDrawable(R.mipmap.unselected_single));

        tab = mTablayout.getTabAt(2);
        tab.setIcon(getResources().getDrawable(R.mipmap.unselected_match));

        tab = mTablayout.getTabAt(3);
        tab.setIcon(getResources().getDrawable(R.mipmap.unselected_discover));

        tab = mTablayout.getTabAt(4);
        tab.setIcon(getResources().getDrawable(R.mipmap.unselected_mine));

    }

    private void initEvents() {

        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    TabLayout.Tab one = mTablayout.getTabAt(0);
                    one.setIcon(getResources().getDrawable(R.mipmap.selected_home));
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTablayout.getTabAt(1)) {
                    TabLayout.Tab two = mTablayout.getTabAt(1);
                    two.setIcon(getResources().getDrawable(R.mipmap.selected_single));
                    mViewPager.setCurrentItem(1);
                } else if (tab == mTablayout.getTabAt(2)) {
                    TabLayout.Tab three = mTablayout.getTabAt(2);
                    three.setIcon(getResources().getDrawable(R.mipmap.selected_match));
                    mViewPager.setCurrentItem(2);
                }else if (tab == mTablayout.getTabAt(3)){
                    TabLayout.Tab four = mTablayout.getTabAt(3);
                    four.setIcon(getResources().getDrawable(R.mipmap.selected_discover));
                    mViewPager.setCurrentItem(3);
                }else if (tab == mTablayout.getTabAt(4)){
                    TabLayout.Tab five = mTablayout.getTabAt(4);
                    five.setIcon(getResources().getDrawable(R.mipmap.selected_mine));
                    mViewPager.setCurrentItem(4);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    TabLayout.Tab one = mTablayout.getTabAt(0);
                    one.setIcon(getResources().getDrawable(R.mipmap.unselected_home));
                } else if (tab == mTablayout.getTabAt(1)) {
                    TabLayout.Tab two = mTablayout.getTabAt(1);
                    two.setIcon(getResources().getDrawable(R.mipmap.unselected_single));
                } else if (tab == mTablayout.getTabAt(2)) {
                    TabLayout.Tab three = mTablayout.getTabAt(2);
                    three.setIcon(getResources().getDrawable(R.mipmap.unselected_match));
                }else if (tab == mTablayout.getTabAt(3)){
                    TabLayout.Tab four = mTablayout.getTabAt(3);
                    four.setIcon(getResources().getDrawable(R.mipmap.unselected_discover));
                }else if (tab == mTablayout.getTabAt(4)){
                    TabLayout.Tab five = mTablayout.getTabAt(4);
                    five.setIcon(getResources().getDrawable(R.mipmap.unselected_mine));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

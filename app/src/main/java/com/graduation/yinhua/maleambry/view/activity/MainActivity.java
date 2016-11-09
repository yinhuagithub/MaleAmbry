package com.graduation.yinhua.maleambry.view.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.fragment.DiscoveryFragment;
import com.graduation.yinhua.maleambry.view.fragment.HomeFragment;
import com.graduation.yinhua.maleambry.view.fragment.MatchFragment;
import com.graduation.yinhua.maleambry.view.fragment.MineFragment;
import com.graduation.yinhua.maleambry.view.fragment.SingleFragment;

/**
 * MainActivity.java
 * Description: 主界面
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MainActivity extends AppCompatActivity {

    BottomNavigationBar bottomNavigationBar;

    HomeFragment home;
    SingleFragment single;
    MatchFragment match;
    DiscoveryFragment discovery;
    MineFragment mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        initWidgets();
        initEvents();
        setDefaultFragment();
    }

    private void initWidgets() {
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.single, "单品"))
                .addItem(new BottomNavigationItem(R.mipmap.match, "搭配"))
                .addItem(new BottomNavigationItem(R.mipmap.discover, "发现"))
                .addItem(new BottomNavigationItem(R.mipmap.mine, "我的"))
                .initialise();
    }

    private void initEvents(){
        bottomNavigationBar.setTabSelectedListener(mTabListener);
    }

    private void setDefaultFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentContent, detectFragment(0));
        transaction.commit();
    }

    /**
     * 检测fragment是否存在，若是不存在就创建
     * @param index
     * @return
     */
    private Fragment detectFragment(int index) {
        switch (index) {
            case 0: {
                if(home == null) {
                    home = new HomeFragment();
                }
                return home;
            }
            case 1: {
                if(single == null) {
                    single = new SingleFragment();
                }
                return single;
            }
            case 2: {
                if(match == null) {
                    match = new MatchFragment();
                }
                return match;
            }
            case 3: {
                if(discovery == null) {
                    discovery = new DiscoveryFragment();
                }
                return discovery;
            }
            case 4: {
                if(mine == null) {
                    mine = new MineFragment();
                }
                return mine;
            }
        }
        return null;
    }

    private BottomNavigationBar.OnTabSelectedListener mTabListener = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragmentContent, detectFragment(position));
            transaction.commit();
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {
        }
    };

}

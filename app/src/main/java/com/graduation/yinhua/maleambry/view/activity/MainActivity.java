package com.graduation.yinhua.maleambry.view.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;
import com.graduation.yinhua.maleambry.view.fragment.DiscoveryFragment;
import com.graduation.yinhua.maleambry.view.fragment.HomeFragment;
import com.graduation.yinhua.maleambry.view.fragment.MatchFragment;
import com.graduation.yinhua.maleambry.view.fragment.MineFragment;
import com.graduation.yinhua.maleambry.view.fragment.SingleFragment;

import butterknife.BindView;

/**
 * MainActivity.java
 * Description: 主界面
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTvTitle;

    HomeFragment home;
    SingleFragment single;
    MatchFragment match;
    DiscoveryFragment discovery;
    MineFragment mine;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDefaultFragment();
    }

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected void initWidgets() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home, R.string.home))
                .addItem(new BottomNavigationItem(R.mipmap.single, R.string.single))
                .addItem(new BottomNavigationItem(R.mipmap.match, R.string.match))
                .addItem(new BottomNavigationItem(R.mipmap.discover, R.string.discovery))
                .addItem(new BottomNavigationItem(R.mipmap.mine, R.string.mine))
                .initialise();
        mTvTitle.setText(R.string.home);
    }

    @Override
    protected void initEvents(){
        mBottomNavigationBar.setTabSelectedListener(mTabListener);
    }

    /**
     * 设置缺省的fragment
     */
    private void setupDefaultFragment() {
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
                mTvTitle.setText(R.string.home);
                return home;
            }
            case 1: {
                if(single == null) {
                    single = new SingleFragment();
                }
                mTvTitle.setText(R.string.single);
                return single;
            }
            case 2: {
                if(match == null) {
                    match = new MatchFragment();
                }
                mTvTitle.setText(R.string.match);
                return match;
            }
            case 3: {
                if(discovery == null) {
                    discovery = new DiscoveryFragment();
                }
                mTvTitle.setText(R.string.discovery);
                return discovery;
            }
            case 4: {
                if(mine == null) {
                    mine = new MineFragment();
                }
                mTvTitle.setText(R.string.mine);
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

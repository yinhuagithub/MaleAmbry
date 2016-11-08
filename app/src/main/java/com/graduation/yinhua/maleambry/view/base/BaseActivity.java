package com.graduation.yinhua.maleambry.view.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * BaseActivity.java
 * Description: 所有子Activity必须继承BaseActivity
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        setImmersiveMode(getImmersiveStatus());
        ButterKnife.bind(this);

        initWidgets();
        bindWidgets();
        initListeners();
    }

    /**
     * 获取沉浸状态
     * @return
     */
    protected abstract boolean getImmersiveStatus();

    /**
     * 设置是否设置沉浸式模式
     * @param isImmersive
     */
    private void setImmersiveMode(boolean isImmersive) {
        if(isImmersive) {
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
    }

    /**
     * 初始化控件，该方法子类可选实现
     */
    protected void initWidgets(){
    }

    /**
     * 初始化控件监听，该方法子类可选实现
     */
    protected void initListeners(){
    }

    /**
     * 绑定控件，设置控件，该方法子类可选实现
     */
    protected void bindWidgets() {
    }

    /**
     * 获取activity布局，该方法子类必须实现
     * @return
     */
    protected abstract int getContentView();

}

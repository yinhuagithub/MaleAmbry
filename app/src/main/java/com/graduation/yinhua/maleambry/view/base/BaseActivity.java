package com.graduation.yinhua.maleambry.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

        ButterKnife.bind(this);

        initWidgets();
        bindWidgets();
        initListeners();

        loadData();
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

    /**
     * 加载所需的数据，该方法子类可选实现
     */
    protected void loadData(){
    }
}

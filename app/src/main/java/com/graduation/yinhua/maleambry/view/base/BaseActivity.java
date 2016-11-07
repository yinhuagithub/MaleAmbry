package com.graduation.yinhua.maleambry.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

        initWidget();
    }

    protected void initWidget() {

    }

    /**
     * 获取activity布局，子类必须实现该方法
     * @return
     */
    protected abstract int getContentView();
}

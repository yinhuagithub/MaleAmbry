package com.graduation.yinhua.maleambry.view.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * LoginActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/20.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mToolbarTitle.setText(R.string.login);
        mIvBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }

}

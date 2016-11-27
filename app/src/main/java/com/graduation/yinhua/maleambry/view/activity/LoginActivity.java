package com.graduation.yinhua.maleambry.view.activity;

import android.content.Intent;
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

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

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
    }

    @OnClick(R.id.iv_back)
    public void back() {
        LoginActivity.this.finish();
    }

    @OnClick(R.id.tv_register)
    public void register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}

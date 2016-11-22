package com.graduation.yinhua.maleambry.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;

/**
 * SettingActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/22.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.tv_modify_password)
    TextView mTvModifyPassword;

    @BindView(R.id.rl_about_male_ambry)
    RelativeLayout mRlAbout;

    @BindView(R.id.btn_quit)
    Button mBtnQuit;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mToolbarTitle.setText(R.string.setting);
    }

    @Override
    protected void initEvents() {
        super.initEvents();

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });

        mTvModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, ModifyPasswordActivity.class));
            }
        });

        mRlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mBtnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
    }
}

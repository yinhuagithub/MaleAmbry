package com.graduation.yinhua.maleambry.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;

/**
 * ModifyPasswordActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/22.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mToolbarTitle.setText(R.string.modify_password);
    }

    @Override
    protected void initEvents() {
        super.initEvents();

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyPasswordActivity.this.finish();
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyPasswordActivity.this.finish();
            }
        });
    }
}

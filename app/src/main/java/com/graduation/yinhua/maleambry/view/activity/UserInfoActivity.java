package com.graduation.yinhua.maleambry.view.activity;

import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * UserInfoActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/21.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mToolbarTitle.setText(R.string.user_info);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        UserInfoActivity.this.finish();
    }

    @OnClick(R.id.tv_finish)
    public void finish() {
        //提交、保存数据

        UserInfoActivity.this.finish();
    }
}

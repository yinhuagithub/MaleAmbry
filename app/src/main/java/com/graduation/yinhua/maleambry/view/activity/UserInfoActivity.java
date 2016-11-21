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
 * UserInfoActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/21.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.tv_finish)
    TextView mTvFinish;

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

    @Override
    protected void initEvents() {
        super.initEvents();

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        mTvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishView();
            }
        });
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back();
//            }
//        });
    }

    public void back() {
        UserInfoActivity.this.finish();
    }


    public void finishView() {
        //提交、保存数据

        UserInfoActivity.this.finish();
    }
}

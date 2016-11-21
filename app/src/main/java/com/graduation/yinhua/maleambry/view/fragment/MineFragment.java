package com.graduation.yinhua.maleambry.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.view.activity.LoginActivity;
import com.graduation.yinhua.maleambry.view.activity.UserInfoActivity;
import com.graduation.yinhua.maleambry.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yinhua on 2016/11/9.
 */
public class MineFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEvents() {
        super.initEvents();

    }

    @OnClick(R.id.iv_mine_avatar)
    public void clickUserAvatar() {
        loginAccount();
    }

    @OnClick(R.id.tv_user_nick)
    public void clickUserNick() {
//        loginAccount();
        viewUserInfo();
    }

    /**
     * 进入登录界面，登录账户
     */
    private void loginAccount() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    /**
     * 进入用户个人信息界面
     */
    private void viewUserInfo() {
        startActivity(new Intent(getContext(), UserInfoActivity.class));
    }
}

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

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.view.activity.FeedBackActivity;
import com.graduation.yinhua.maleambry.view.activity.LoginActivity;
import com.graduation.yinhua.maleambry.view.activity.SettingActivity;
import com.graduation.yinhua.maleambry.view.activity.UserInfoActivity;
import com.graduation.yinhua.maleambry.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yinhua on 2016/11/9.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.tv_user_nick)
    TextView mTvUserNick;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEvents() {
        super.initEvents();

    }

    @Override
    public void onResume() {
        super.onResume();
        User user = MaleAmbryApp.getUser();
        if(user != null && user.isLogin()) {
            mTvUserNick.setText(user.getNick_name());
        } else {
            mTvUserNick.setText(R.string.visitor);
        }
    }

    @OnClick(R.id.ll_user)
    public void clickUserAvatar() {
        User user = MaleAmbryApp.getUser();
        if(user != null && user.isLogin()) {
            viewUserInfo();
        } else {
            loginAccount();
        }
    }

    @OnClick(R.id.rl_mine_favorite)
    public void enterMyFavorites() {

    }

    @OnClick(R.id.rl_mine_feedback)
    public void feedback() {
        startActivity(new Intent(getContext(), FeedBackActivity.class));
    }

    @OnClick(R.id.rl_mine_settings)
    public void setting() {
        startActivity(new Intent(getContext(), SettingActivity.class));
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

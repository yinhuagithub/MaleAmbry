package com.graduation.yinhua.maleambry.view.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.listeners.OnLoginListener;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * LoginActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/20.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String DEFAULT_APP_TOKEN = "MaleAmbry";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.et_login_name)
    EditText mEtLoginName;

    @BindView(R.id.et_password)
    EditText mEtPassword;

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

    @OnClick(R.id.tv_forgot_password)
    public void forgotPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.tv_login)
    public void login() {
        String login_name = mEtLoginName.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(login_name) && !TextUtils.isEmpty(password) && verifyPhone(login_name)) {
            User.loginAccount(DEFAULT_APP_TOKEN, login_name, password, new OnLoginListener() {
                @Override
                public void onSuccess() {
                    LoginActivity.this.finish();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(LoginActivity.this, "账号或者密码错误，请重新输入。", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    private boolean verifyPhone(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return  matcher.matches();
    }

}

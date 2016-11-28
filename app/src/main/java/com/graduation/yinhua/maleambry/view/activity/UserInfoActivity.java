package com.graduation.yinhua.maleambry.view.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
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

    @BindView(R.id.et_user_name)
    EditText mEtUserName;

    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;

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

        User user = MaleAmbryApp.getUser();
        if(user != null && user.isLogin()) {
            mEtUserName.setText(user.getNick_name());
            mEtUserPhone.setText(user.getPhone());
        }
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
    }

    public void back() {
        UserInfoActivity.this.finish();
    }

    public void finishView() {
        String nick_name = mEtUserName.getText().toString().trim();
        String phone = mEtUserPhone.getText().toString().trim();

        if(!TextUtils.isEmpty(nick_name) && !TextUtils.isEmpty(phone) && verifyPhone(phone)) {
            User user = MaleAmbryApp.getUser();
            if(user != null && user.isLogin()) {
                modifyUserInfo(user.getApp_token(), user.getNick_name(), user.getPhone());
            }
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

    /**
     * 修改用户信息
     */
    private void modifyUserInfo(String app_token, String nick_name, String phone) {
        MaleAmbryRetrofit.getInstance().modifyUserInfo(app_token, nick_name, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<User>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<User> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            MaleAmbryApp.setUser(responseMessage.getResults());
                            UserInfoActivity.this.finish();
                        } else {
                            Toast.makeText(UserInfoActivity.this, "修改用户信息失败，请重试。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

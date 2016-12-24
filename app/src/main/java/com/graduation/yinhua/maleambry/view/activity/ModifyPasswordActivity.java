package com.graduation.yinhua.maleambry.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.graduation.yinhua.maleambry.utils.NetworkUtil;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;

    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;

    @BindView(R.id.et_confirm_password)
    EditText mEtConfirmPassword;

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

    @OnClick(R.id.btn_confirm)
    public void confirm() {
        String old_password = mEtOldPassword.getText().toString().trim();
        String new_password = mEtNewPassword.getText().toString().trim();
        String confirm_password = mEtConfirmPassword.getText().toString().trim();
        if(!TextUtils.isEmpty(old_password) && !TextUtils.isEmpty(new_password) && !TextUtils.isEmpty(confirm_password)) {
            if(new_password.equals(confirm_password)) {
                User user = MaleAmbryApp.getUser();
                modifyPassword(user.getApp_token(), old_password, new_password, user.getPhone());
            }
        }
    }

    /**
     * 修改密码
     * @param app_token
     * @param old_password
     * @param new_password
     * @param phone
     */
    private void modifyPassword(String app_token, String old_password, String new_password, String phone) {
        if(!NetworkUtil.checkNetwork(ModifyPasswordActivity.this)) {
            return;
        }
        MaleAmbryRetrofit.getInstance().modifyPassword(app_token, old_password, new_password, phone)
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
                            ModifyPasswordActivity.this.finish();
                        } else {
                            Toast.makeText(ModifyPasswordActivity.this, "修改密码失败，请检查密码是否正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

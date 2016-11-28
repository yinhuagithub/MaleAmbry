package com.graduation.yinhua.maleambry.view.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ForgotPasswordActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ForgotPasswordActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int VERIFY_SUCCESS = 1000;
    private static final int VERIFY_FAILURE = 1001;
    private static final int DEFAULT_COUNT_DOWN = 60;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.et_phone)
    EditText mEtPhone;

    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;

    @BindView(R.id.et_sms_code)
    EditText mEtSmsCode;

    @BindView(R.id.tv_sms_code)
    TextView mTvSmsCode;

    private int countDown = DEFAULT_COUNT_DOWN;
    private EventHandler mEventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = mHandler.obtainMessage();
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    msg.what = VERIFY_SUCCESS;
                } else {
                    msg.what = VERIFY_FAILURE;
                    msg.obj = "验证码错误，请输入正确的验证码。";
                }
                mHandler.sendMessage(msg);
            }
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == VERIFY_FAILURE) {
                Toast.makeText(ForgotPasswordActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
            } else if(msg.what == VERIFY_SUCCESS) {
                String phone = mEtPhone.getText().toString().trim();
                String password = mEtNewPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    foundPassword(phone, password);
                }
            } else {
                countDown--;
                mTvSmsCode.setText("" + countDown + "s");

                if (countDown != 0) {
                    mHandler.sendMessageDelayed(obtainMessage(), 1000);
                } else {
                    countDown = DEFAULT_COUNT_DOWN;
                    mTvSmsCode.setText(R.string.sms_code);
                    mTvSmsCode.setEnabled(true);
                    mTvSmsCode.setTextColor(getResources().getColor(R.color.white));
                }
            }
        }
    };

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mToolbarTitle.setText(R.string.found_password);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        SMSSDK.registerEventHandler(mEventHandler);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordActivity.this.finish();
            }
        });

        mTvSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEtPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(phone) && verifyPhone(phone)) {
                    getSMSCode(phone);
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
     * 获取短信验证码
     */
    private void getSMSCode(String phone) {
        countDownTask();
        SMSSDK.getVerificationCode("86", phone);
    }

    /**
     * 倒计时任务
     */
    private void countDownTask() {
        mTvSmsCode.setText("" + countDown + "s");
        mTvSmsCode.setEnabled(false);
        mTvSmsCode.setTextColor(getResources().getColor(R.color.black));
        Message msg = mHandler.obtainMessage();
        mHandler.sendMessageDelayed(msg, 1000);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm() {
        String phone = mEtPhone.getText().toString().trim();
        String password = mEtNewPassword.getText().toString().trim();
        String code = mEtSmsCode.getText().toString().trim();
        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(code)) {
            SMSSDK.submitVerificationCode("86", phone, code);
        } else {
            Toast.makeText(ForgotPasswordActivity.this, "请输入手机号、新密码和验证码。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 找回密码
     */
    private void foundPassword(String phone, String password) {
        MaleAmbryRetrofit.getInstance().forgotPassword(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<User>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<User> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            MaleAmbryApp.setUser(responseMessage.getResults());
                            ForgotPasswordActivity.this.finish();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "找不到该手机号码的账号，请检查手机号。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}

package com.graduation.yinhua.maleambry.view.activity;

import android.content.Intent;
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
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * FeedBackActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/22.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.et_contact)
    EditText mEtContact;

    @BindView(R.id.et_content)
    EditText mEtContent;

    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mToolbarTitle.setText(R.string.user_feedback);
    }

    @Override
    protected void initEvents() {
        super.initEvents();

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
    }

    public void back() {
        FeedBackActivity.this.finish();
    }

    public void confirm() {
        String contact = mEtContact.getText().toString().trim();
        String content = mEtContent.getText().toString().trim();

        if(!TextUtils.isEmpty(contact) && !TextUtils.isEmpty(content)) {
            feedback(contact, content);
        }
    }

    /**
     * 修改用户信息
     */
    private void feedback(String contact, String content) {
        MaleAmbryRetrofit.getInstance().feedback(contact, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<String> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            Toast.makeText(FeedBackActivity.this, "意见已发送，感谢的您真挚的反馈。", Toast.LENGTH_SHORT).show();
                            FeedBackActivity.this.finish();
                        } else {
                            Toast.makeText(FeedBackActivity.this, "意见发送失败，请重试。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

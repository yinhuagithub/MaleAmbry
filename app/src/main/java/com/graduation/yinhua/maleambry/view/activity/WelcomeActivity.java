package com.graduation.yinhua.maleambry.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.contract.WelcomeContract;
import com.graduation.yinhua.maleambry.presenter.WelcomePresenter;
import com.graduation.yinhua.maleambry.view.base.BaseMVPActivity;
import com.graduation.yinhua.maleambry.view.widgets.ParallaxContainer;
import com.graduation.yinhua.maleambry.view.widgets.ParallaxImageView;
import com.graduation.yinhua.maleambry.view.widgets.RoundProgressBar;

/**
 * WelcomeActivity.java
 * Description: 欢迎页
 * <p/>
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class WelcomeActivity extends BaseMVPActivity<WelcomeContract.View, WelcomePresenter> implements WelcomeContract.View {

    private static final String FIRST_RUN = "isFirstRun";

    private ParallaxContainer mParallaxContainer;
    private ParallaxImageView mPivEnterMain;
    private ImageView mIvMan;

    private RoundProgressBar mRoundProgressBar;

    //APP是否第一次运行
    private boolean isFirstRun;

    @Override
    protected int getContentView() {
        isFirstRun = MaleAmbryApp.getConfigSP().getBoolean(FIRST_RUN, true);

        if(isFirstRun) {
            return R.layout.activity_welcome_1;
        } else {
            return R.layout.activity_welcome_2;
        }
    }

    @Override
    protected boolean getImmersiveStatus() {
        return true;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        if(isFirstRun) {
            mParallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);
            mIvMan = (ImageView) findViewById(R.id.iv_man);

            mParallaxContainer.setAnimImageView(mIvMan);
            mIvMan.setVisibility(View.VISIBLE);
            mParallaxContainer.setupChildren(getLayoutInflater(), R.layout.view_intro_1, R.layout.view_intro_2, R.layout.view_intro_3, R.layout.view_intro_4);
        } else {
            mRoundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();

        if(isFirstRun) {
            mPivEnterMain = (ParallaxImageView) findViewById(R.id.piv_enter_main);
            mPivEnterMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enterMainActivity();
                }
            });
        } else {
            mRoundProgressBar.setCountDownTimerListener(new RoundProgressBar.CountDownTimerListener() {
                @Override
                public void onFinishCount() {
                    enterMainActivity();
                }
            });
            mRoundProgressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRoundProgressBar.cancel();
                    enterMainActivity();
                }
            });
            mRoundProgressBar.start();
        }
    }

    @Override
    public void enterMainActivity() {
        MaleAmbryApp.getConfigSP().putBoolean(FIRST_RUN, false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter();
    }

    @Override
    public void loadLocalData() {
    }
}

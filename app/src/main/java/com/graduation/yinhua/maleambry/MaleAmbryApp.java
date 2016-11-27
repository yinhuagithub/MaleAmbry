package com.graduation.yinhua.maleambry;

import android.app.Application;
import android.content.Context;

import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.utils.SPUtil;

import cn.smssdk.SMSSDK;

/**
 * MaleAmbryApp.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MaleAmbryApp extends Application {

    private static final String TAG = MaleAmbryApp.class.getSimpleName();
    private static final String SP_CONFIG_NAME = "config";
    private static final String SMSSDK_APP_KEY = "19624a3fbc972";
    private static final String SMSSDK_APP_SECRET = "fe2aed794f18fddac89382689b19cca7";

    private static SPUtil mConfigSP;
    private static Context mContext;

    private static User mUser = null;

    @Override
    public void onCreate() {
        super.onCreate();

        SMSSDK.initSDK(this, SMSSDK_APP_KEY, SMSSDK_APP_SECRET);
        mContext = getApplicationContext();
        mConfigSP = new SPUtil(mContext, SP_CONFIG_NAME);
    }

    public static SPUtil getConfigSP() {
        return mConfigSP;
    }

    public static Context getContext() {
        return mContext;
    }

    public static User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        mUser =user;
        saveUser();
    }

    private static void saveUser() {
        mConfigSP.putInt("uid", mUser.getUid());
        mConfigSP.putString("nick_name", mUser.getNick_name());
        mConfigSP.putString("login_name", mUser.getLogin_name());
        mConfigSP.putString("password", mUser.getPassword());
        mConfigSP.putString("app_token", mUser.getApp_token());
        mConfigSP.putLong("timestamp", mUser.getTimestamp());
        mConfigSP.putString("phone", mUser.getPhone());
    }
}

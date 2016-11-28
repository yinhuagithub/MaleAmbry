package com.graduation.yinhua.maleambry;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.utils.SPUtil;

import cn.smssdk.SMSSDK;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MaleAmbryApp.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MaleAmbryApp extends Application {

    private static final String TAG = MaleAmbryApp.class.getSimpleName();
    private static final String DEFAULT_APP_TOKEN = "MaleAmbry";
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

        User user = getUserInfo();
        loginAccount(user.getApp_token(), user.getLogin_name(), user.getPassword());
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
        mUser.setLogin(true);
        saveUser();
    }

    /**
     * 保存用户信息到SharedPreferences中
     */
    private static void saveUser() {
        mConfigSP.putInt("uid", mUser.getUid());
        mConfigSP.putString("nick_name", mUser.getNick_name());
        mConfigSP.putString("login_name", mUser.getLogin_name());
        mConfigSP.putString("password", mUser.getPassword());
        mConfigSP.putString("app_token", mUser.getApp_token());
        mConfigSP.putLong("timestamp", mUser.getTimestamp());
        mConfigSP.putString("phone", mUser.getPhone());
    }

    /**
     * 清空本地用户信息
     */
    public static void clearUserInfo() {
        mConfigSP.remove("uid");
        mConfigSP.remove("nick_name");
        mConfigSP.remove("login_name");
        mConfigSP.remove("password");
        mConfigSP.remove("app_token");
        mConfigSP.remove("timestamp");
        mConfigSP.remove("phone");
        mUser = null;
    }

    /**
     * 从SharedPreferences中获取user信息
     */
    private User getUserInfo() {
        User user = null;
        String app_token = mConfigSP.getString("app_token", DEFAULT_APP_TOKEN);
        if(!app_token.equals(DEFAULT_APP_TOKEN)) {
            if(user == null) {
                user = new User();
            }
            user.setApp_token(app_token);
            user.setLogin(false);
            user.setUid(mConfigSP.getInt("uid", 0));
            user.setNick_name(mConfigSP.getString("nick_name", ""));
            user.setLogin_name(mConfigSP.getString("login_name", ""));
            user.setPassword(mConfigSP.getString("password", ""));
            user.setTimestamp(mConfigSP.getLong("timestamp", 0));
            user.setPhone(mConfigSP.getString("phone", ""));
        }
        return  user;
    }

    /**
     * 登录账号
     * @param app_token
     * @param loginName
     * @param password
     */
    private void loginAccount(String app_token, String loginName, String password) {
        MaleAmbryRetrofit.getInstance().login(app_token, loginName, password)
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
                        }
                    }
                });
    }
}

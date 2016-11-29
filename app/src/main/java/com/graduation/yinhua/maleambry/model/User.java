package com.graduation.yinhua.maleambry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class User {

    private boolean isLogin;

    @SerializedName("uid")
    @Expose
    private int uid;

    @SerializedName("nick_name")
    @Expose
    private String nick_name;

    @SerializedName("login_name")
    @Expose
    private String login_name;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("app_token")
    @Expose
    private String app_token;

    @SerializedName("timestamp")
    @Expose
    private long timestamp;

    @SerializedName("phone")
    @Expose
    private String phone;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 登录账号
     * @param app_token
     * @param loginName
     * @param password
     */
    public static void loginAccount(String app_token, String loginName, String password) {
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
                            User user = responseMessage.getResults();
                            MaleAmbryApp.setUser(user);
                            FavoSingle.fetchFavoSingle(user.getApp_token());
                            FavoMatch.fetchFavoMatch(user.getApp_token());
                            FavoDiscovery.fetchFavoDiscovery(user.getApp_token());
                        }
                    }
                });
    }
}

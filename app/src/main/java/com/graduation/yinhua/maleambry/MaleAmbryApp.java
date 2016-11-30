package com.graduation.yinhua.maleambry;

import android.app.Application;
import android.content.Context;

import com.graduation.yinhua.maleambry.model.FavoDiscovery;
import com.graduation.yinhua.maleambry.model.FavoMatch;
import com.graduation.yinhua.maleambry.model.FavoSingle;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.utils.SPUtil;

import java.util.List;

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
    private static final String DEFAULT_APP_TOKEN = "MaleAmbry";
    private static final String SP_CONFIG_NAME = "config";
    private static final String SMSSDK_APP_KEY = "19624a3fbc972";
    private static final String SMSSDK_APP_SECRET = "fe2aed794f18fddac89382689b19cca7";

    private static SPUtil mConfigSP;
    private static Context mContext;

    private static User mUser = null;
    private static List<FavoSingle> mFavoSingleList = null;
    private static List<FavoMatch> mFavoMatchList = null;
    private static List<FavoDiscovery> mFavoDiscoveryList = null;

    @Override
    public void onCreate() {
        super.onCreate();

        SMSSDK.initSDK(this, SMSSDK_APP_KEY, SMSSDK_APP_SECRET);
        mContext = getApplicationContext();
        mConfigSP = new SPUtil(mContext, SP_CONFIG_NAME);

        User user = getUserInfo();
        if(user != null) {
            User.loginAccount(user.getApp_token(), user.getLogin_name(), user.getPassword());
        }
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

    public static List<FavoSingle> getmFavoSingleList() {
        return mFavoSingleList;
    }

    public static void setmFavoSingleList(List<FavoSingle> mFavoSingleList) {
        MaleAmbryApp.mFavoSingleList = mFavoSingleList;
    }

    public static List<FavoMatch> getmFavoMatchList() {
        return mFavoMatchList;
    }

    public static void setmFavoMatchList(List<FavoMatch> mFavoMatchList) {
        MaleAmbryApp.mFavoMatchList = mFavoMatchList;
    }

    public static List<FavoDiscovery> getmFavoDiscoveryList() {
        return mFavoDiscoveryList;
    }

    public static void setmFavoDiscoveryList(List<FavoDiscovery> mFavoDiscoveryList) {
        MaleAmbryApp.mFavoDiscoveryList = mFavoDiscoveryList;
    }

    /**
     * 判断是否包含单品
     * @param sid
     * @return
     */
    public static boolean containsSingle(int sid) {
        if(MaleAmbryApp.getmFavoSingleList() != null) {
            FavoSingle favoSingle = new FavoSingle();
            favoSingle.setSid(sid);
            return MaleAmbryApp.getmFavoSingleList().contains(favoSingle);
        } else {
            return false;
        }
    }

    /**
     * 判断是否包含搭配
     * @param mid
     * @return
     */
    public static boolean containsMatch(int mid) {
        if(MaleAmbryApp.getmFavoMatchList() != null){
            FavoMatch favoMatch = new FavoMatch();
            favoMatch.setMid(mid);
            return MaleAmbryApp.getmFavoMatchList().contains(favoMatch);
        } else {
            return false;
        }
    }

    /**
     * 判断是否包含发现
     * @param did
     * @return
     */
    public static boolean containsDiscovery(int did) {
        if(MaleAmbryApp.getmFavoDiscoveryList() != null) {
            FavoDiscovery favoDiscovery = new FavoDiscovery();
            favoDiscovery.setDid(did);
            return MaleAmbryApp.getmFavoDiscoveryList().contains(favoDiscovery);
        } else {
            return false;
        }
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

            if(System.currentTimeMillis() - user.getTimestamp() > 30 * 24 * 60 * 60 * 100) {    //app_token过期
                user = null;
            }
        }
        return  user;
    }

}

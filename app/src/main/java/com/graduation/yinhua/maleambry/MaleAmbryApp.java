package com.graduation.yinhua.maleambry;

import android.app.Application;
import android.content.Context;

import com.graduation.yinhua.maleambry.utils.SPUtil;

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

    private static SPUtil mConfigSP;
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mConfigSP = new SPUtil(mContext, SP_CONFIG_NAME);
    }

    public static SPUtil getConfigSP() {
        return mConfigSP;
    }

    public static Context getAppContext() {
        return mContext;
    }
}

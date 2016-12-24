package com.graduation.yinhua.maleambry.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.R;

/**
 * NetworkUtil.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/12/14.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class NetworkUtil {

    /**
     * 查询网络
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context, View view) {
        if(!isNetworkAvailable(context)) {
            Snackbar.make(view, R.string.network_anomaly, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 查询网络
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context) {
        if(!isNetworkAvailable(context)) {
            Toast.makeText(context.getApplicationContext(), R.string.network_anomaly, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查当前网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        Context appContext = context.getApplicationContext();

        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) {
            return false;
        }

        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if(allNetworkInfo != null && allNetworkInfo.length > 0) {
            for (int index = 0; index < allNetworkInfo.length; index++) {
                if(allNetworkInfo[index].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }
}

package com.graduation.yinhua.maleambry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * FavoMatch.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/29.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class FavoMatch {

    @SerializedName("fmid")
    @Expose
    private int fmid;

    @SerializedName("mid")
    @Expose
    private int mid;

    @SerializedName("uid")
    @Expose
    private int uid;

    public int getFmid() {
        return fmid;
    }

    public void setFmid(int fmid) {
        this.fmid = fmid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoMatch favoMatch = (FavoMatch) o;

        return mid == favoMatch.mid;
    }

    @Override
    public int hashCode() {
        int result = fmid;
        result = 31 * result + mid;
        result = 31 * result + uid;
        return result;
    }

    /**
     * 获取搭配收藏列表
     * @param app_token
     */
    public static void fetchFavoMatch(String app_token) {
        MaleAmbryRetrofit.getInstance().getFavoMid(app_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<FavoMatch>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseMessage<List<FavoMatch>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            MaleAmbryApp.setmFavoMatchList(responseMessage.getResults());
                        }
                    }
                });
    }

    /**
     * 添加到搭配收藏列表
     * @param app_token
     * @param mid
     */
    public static void addFavoMid(String app_token, int mid) {
        MaleAmbryRetrofit.getInstance().addFavoMid(app_token, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<String>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<String> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                        }
                    }
                });
    }

    /**
     * 从搭配收藏列表移除
     * @param app_token
     * @param mid
     */
    public static void removeFavoMid(String app_token, int mid) {
        MaleAmbryRetrofit.getInstance().removeFavoMid(app_token, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<String>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<String> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                        }
                    }
                });
    }
}

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
 * FavoDiscovery.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/29.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class FavoDiscovery {

    @SerializedName("fdid")
    @Expose
    private int fdid;

    @SerializedName("did")
    @Expose
    private int did;

    @SerializedName("uid")
    @Expose
    private int uid;

    public int getFdid() {
        return fdid;
    }

    public void setFdid(int fdid) {
        this.fdid = fdid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
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

        FavoDiscovery that = (FavoDiscovery) o;

        return did == that.did;
    }

    @Override
    public int hashCode() {
        int result = fdid;
        result = 31 * result + did;
        result = 31 * result + uid;
        return result;
    }

    public static void fetchFavoDiscovery(String app_token) {
        MaleAmbryRetrofit.getInstance().getFavoDid(app_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<FavoDiscovery>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseMessage<List<FavoDiscovery>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            MaleAmbryApp.setmFavoDiscoveryList(responseMessage.getResults());
                        }
                    }
                });
    }
}

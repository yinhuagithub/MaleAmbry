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
 * FavoSingle.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/29.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class FavoSingle {

    @SerializedName("fsid")
    @Expose
    private int fsid;

    @SerializedName("sid")
    @Expose
    private int sid;

    @SerializedName("uid")
    @Expose
    private int uid;

    public int getFsid() {
        return fsid;
    }

    public void setFsid(int fsid) {
        this.fsid = fsid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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

        FavoSingle that = (FavoSingle) o;

        return sid == that.sid;
    }

    @Override
    public int hashCode() {
        int result = fsid;
        result = 31 * result + sid;
        result = 31 * result + uid;
        return result;
    }

    public static void fetchFavoSingle(String app_token) {
        MaleAmbryRetrofit.getInstance().getFavoSid(app_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<FavoSingle>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<List<FavoSingle>> responseMessage) {
                        if (responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            MaleAmbryApp.setmFavoSingleList(responseMessage.getResults());
                        }
                    }
                });
    }
}

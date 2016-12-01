package com.graduation.yinhua.maleambry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * ThumbSingle.java
 * Description:
 * <p>
 * Created by yinhua on 2016/12/1.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ThumbSingle {

    @SerializedName("tsid")
    @Expose
    private int tsid;

    @SerializedName("sid")
    @Expose
    private int sid;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public int getTsid() {
        return tsid;
    }

    public void setTsid(int tsid) {
        this.tsid = tsid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

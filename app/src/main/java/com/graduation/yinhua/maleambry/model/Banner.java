package com.graduation.yinhua.maleambry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Banner.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/27.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class Banner {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("target_url")
    @Expose
    private String target_url;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }
}

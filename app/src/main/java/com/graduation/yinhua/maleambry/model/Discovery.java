package com.graduation.yinhua.maleambry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Discovery.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/11.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class Discovery {

    @SerializedName("did")
    @Expose
    private int did;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("thumb_url")
    @Expose
    private String thumb;

    @SerializedName("viewed")
    @Expose
    private int viewed;

    @SerializedName("detail_url")
    @Expose
    private String detail_url;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discovery discovery = (Discovery) o;

        return did == discovery.did;

    }

    @Override
    public int hashCode() {
        return did;
    }
}

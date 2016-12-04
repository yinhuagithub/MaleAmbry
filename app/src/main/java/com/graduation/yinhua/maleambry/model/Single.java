package com.graduation.yinhua.maleambry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Single.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/20.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class Single {

    @SerializedName("sid")
    @Expose
    private int sid;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("thumb_url")
    @Expose
    private String thumb_url;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("favorite_count")
    @Expose
    private int favorite_count;

    @SerializedName("target_url")
    @Expose
    private String target_url;

    @SerializedName("shop_url")
    @Expose
    private String shop_url;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Single single = (Single) o;
        return sid == single.sid;
    }

    @Override
    public int hashCode() {
        return sid;
    }
}

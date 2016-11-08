package com.graduation.yinhua.maleambry.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MaleAmbryResponse.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MaleAmbryResponse {

    @SerializedName("status_code")
    private int status_code;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "MaleAmbryResponse{" +
                "status_code=" + status_code +
                '}';
    }
}

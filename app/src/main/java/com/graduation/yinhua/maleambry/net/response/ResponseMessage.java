package com.graduation.yinhua.maleambry.net.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ResponseMessage.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ResponseMessage<T> {

    @SerializedName("status_code")
    @Expose
    private int status_code;

    @SerializedName("results")
    @Expose
    private T results;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "status_code=" + status_code +
                '}';
    }
}

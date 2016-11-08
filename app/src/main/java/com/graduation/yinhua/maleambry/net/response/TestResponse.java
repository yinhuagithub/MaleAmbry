package com.graduation.yinhua.maleambry.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TestResponse.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class TestResponse extends MaleAmbryResponse {

    @SerializedName("results")
    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "TestResponse{" +
                "results=" + results +
                '}';
    }
}

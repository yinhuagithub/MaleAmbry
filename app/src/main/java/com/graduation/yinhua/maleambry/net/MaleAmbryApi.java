package com.graduation.yinhua.maleambry.net;

import com.graduation.yinhua.maleambry.net.response.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * MaleAmbryApi.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public interface MaleAmbryApi {

    /**
     * 测试使用的api接口
     * @return
     */
    @GET("testtest")
    Call<TestResponse> getTestTestData();
}

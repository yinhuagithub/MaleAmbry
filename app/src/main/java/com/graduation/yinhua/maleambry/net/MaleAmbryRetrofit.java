package com.graduation.yinhua.maleambry.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MaleAmbryRetrofit.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MaleAmbryRetrofit {

    private static final String BASE_URL = "http://10.0.2.2:8080/MaleAmbry/";
    private final MaleAmbryApi mMaleAmbryApi;
    private final Gson mGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .create();

    private MaleAmbryRetrofit(){
        //init okHttpClient
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(12, TimeUnit.SECONDS)
                .build();

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        mMaleAmbryApi = retrofit.create(MaleAmbryApi.class);
    }

    public static MaleAmbryApi getInstance(){
        return MaleAmbryApiSingleton.sInstance;
    }

    private static class MaleAmbryApiSingleton{
        private static MaleAmbryApi sInstance = new MaleAmbryRetrofit().mMaleAmbryApi;
    }
}

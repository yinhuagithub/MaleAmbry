package com.graduation.yinhua.maleambry.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MaleAmbryRetrofit.java
 * Description:使用静态内部类实现单例模式，保证线程安全
 * <p/>
 * Created by yinhua on 2016/11/8.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MaleAmbryRetrofit {

//    private static final String BASE_URL = "http://10.0.2.2:8080/MaleAmbry/";
//    private static final String BASE_URL = "http://192.168.1.100:8080/MaleAmbry/";
    private static final String BASE_URL = "http://192.169.60.178:8080/MaleAmbry/";
    public static final String BASE_IMAGE_URL = BASE_URL + "images/";
    private final MaleAmbryApi mMaleAmbryApi;

    private MaleAmbryRetrofit(){
        //init okHttpClient
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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

package com.graduation.yinhua.maleambry.net;

import com.graduation.yinhua.maleambry.model.Banner;
import com.graduation.yinhua.maleambry.model.Discovery;
import com.graduation.yinhua.maleambry.model.Match;
import com.graduation.yinhua.maleambry.model.Single;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.net.response.TestResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

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

    //获取banner信息
    @GET("banner")
    Observable<ResponseMessage<List<Banner>>> getBanner();

    //获取吸睛发现信息
    @GET("recommand_discovery")
    Observable<ResponseMessage<Discovery>> getRecommandDiscovery(@Query("page") int page);

    //获取潮流搭配信息
    @GET("recommand_match")
    Observable<ResponseMessage<Match>> getRecommandMatch(@Query("page") int page);

    //获取今日精选信息
    @GET("recommand_single")
    Observable<ResponseMessage<List<Single>>> getRecommandSingle();

    //获取某风格第n页搭配信息
    @GET("single")
    Observable<ResponseMessage<List<Single>>> getSingle(@Query("style") int style, @Query("page") int page);

    //获取某风格第n页搭配信息
    @GET("match")
    Observable<ResponseMessage<List<Match>>> getMatch(@Query("style") int style, @Query("page") int page);

    //获取第n页发现信息
    @GET("discovery")
    Observable<ResponseMessage<List<Discovery>>> getDiscovery(@Query("page") int page);

    //用户注册
    @POST("register")
    Observable<ResponseMessage<User>> register(@Query("login_name") String login_name, @Query("password") String password, @Query("phone") String phone);

    //用户登录
    @POST("login")
    Observable<ResponseMessage<User>> login(@Query("app_token") String app_token, @Query("login_name") String login_name, @Query("password") String password);

    //找回密码
    @GET("forgot_password")
    Observable<ResponseMessage<User>> forgotPassword(@Query("phone") String phone, @Query("new_psd") String new_psd);

    //修改密码
    @POST("modify_password")
    Observable<ResponseMessage<User>> modifyPassword(@Query("app_token") String app_token, @Query("old_psd") String old_psd, @Query("new_psd") String new_psd, @Query("phone") String phone);

    //修改用户信息
    @POST("modify_user_info")
    Observable<ResponseMessage<User>> modifyUserInfo(@Query("app_token") String app_token, @Query("nick_name") String nick_name, @Query("phone") String phone);

    //反馈意见
    @POST("feedback")
    Observable<ResponseMessage<String>> feedback(@Query("contact") String contact, @Query("content") String content);

    //单品收藏
    @POST("favo_single")
    Observable<ResponseMessage<List<Single>>> getFavoriteSingle(@Query("app_token") String app_token, @Query("page") int page);

    //搭配收藏
    @POST("favo_match")
    Observable<ResponseMessage<List<Match>>> getFavoriteMatch(@Query("app_token") String app_token, @Query("page") int page);

    //发现收藏
    @POST("favo_discovery")
    Observable<ResponseMessage<List<Discovery>>> getFavoriteDiscovery(@Query("app_token") String app_token, @Query("page") int page);
}

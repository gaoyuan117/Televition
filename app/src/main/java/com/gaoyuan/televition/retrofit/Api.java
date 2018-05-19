package com.gaoyuan.televition.retrofit;

import com.gaoyuan.televition.ui.bean.AnalysisBean;
import com.gaoyuan.televition.ui.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 2017/3/27.
 */

public interface Api {
    public static final String BASE_URL = "http://vip.8eym3.cn";

    //注册
    @FormUrlEncoded
    @POST("/api/public/register")
    Observable<HttpResult<CommonBean>> register(@Field("mobile") String mobile,
                                    @Field("password") String password);

    //登陆
    @FormUrlEncoded
    @POST("/api/public/login")
    Observable<HttpResult<LoginBean>> login(@Field("mobile") String mobile,
                                            @Field("password") String password);

    //解析列表
    @FormUrlEncoded
    @POST("/api/news/index")
    Observable<HttpResult<AnalysisBean>> index(@Field("mobile") String mobile);

    //解析列表
    @FormUrlEncoded
    @POST("/api/public/protocol")
    Observable<HttpResult<LoginBean>> protocol(@Field("mobile") String mobile,
                                            @Field("password") String password);

 //解析列表
    @FormUrlEncoded
    @POST("api/user/getUserInfo")
    Observable<HttpResult<LoginBean>> getUserInfo(@Field("token") String mobile);



}
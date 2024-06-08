package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call //封装HTTP请求的响应
import retrofit2.http.GET //发送GET请求
import retrofit2.http.Query //在GET请求的URL中添加查询参数

interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN") //请求中包含API令牌
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    //发送一个包含查询参数query的GET请求，并返回一个用于异步获取PlaceResponse的Call对象。
}
package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {

    private val weatherService = ServiceCreator.create(WeatherService::class.java)
    //访问天气相关的API
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()
    //获取未来的天气信息
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()
    //获取实时的天气信息
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    //访问地点相关的API
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    //发起搜索城市数据请求
    private suspend fun <T> Call<T>.await(): T { //将Retrofit的网络请求转换为挂起函数
        return suspendCoroutine { continuation -> //将回调基的异步操作转换为协程挂起操作
            enqueue(object : Callback<T> { //异步地执行网络请求，并传入一个Callback接口的实现处理响应
                override fun onResponse(call: Call<T>, response: Response<T>) { //在请求成功返回时被调用
                    val body = response.body() //获取请求的响应体
                    if (body != null) continuation.resume(body)
                    //如果响应体不为空，恢复挂起的协程，并将响应体作为结果返回
                    else continuation.resumeWithException(RuntimeException("响应体为空"))
                    //如果响应体为空，恢复挂起的协程，并抛出一个RuntimeException异常
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    //在请求失败时被调用
                    continuation.resumeWithException(t)
                    //恢复挂起的协程，并抛出请求失败时的异常
                }
            })
        }
    }

}
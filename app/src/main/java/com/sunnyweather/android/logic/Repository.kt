package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
//封装数据处理逻辑，包括网络请求和本地数据的访问
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
    //接收查询字符串,使用fire函数在IO线程中执行网络请求
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
    //进行网络请求，返回地点响应数据
        if (placeResponse.status == "ok") {
            //判断是否响应
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String, placeName: String) = fire(Dispatchers.IO) {
        //接收经纬度和地点名称,使用fire函数在IO线程中执行并行的网络请求
        coroutineScope {
            val deferredRealtime = async {
                //创建并启动一个异步任务，用于获取实时天气数据
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                //创建并启动另一个异步任务，用于获取未来天气数据
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            //等待实时天气请求完成，并获取其响应
            val dailyResponse = deferredDaily.await()
            //等待未来天气请求完成，并获取其响应
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                //判断2个响应的状态
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                //创建Weather对象，包含实时天气和未来天气信息
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    //定义savePlace函数,将Place对象保存在本地数据库中
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    //定义getSavedPlace函数,从本地数据库中获取已保存的地点
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
    //定义isPlaceSaved函数,检查是否有地点已保存
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        //定义一个泛型私有函数fire,接受协程上下文和挂起函数块
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

}
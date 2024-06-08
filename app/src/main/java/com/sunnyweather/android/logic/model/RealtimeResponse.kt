package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

class RealtimeResponse(val status: String, val result: Result) {
    //包含status和result两个属性，表示实时天气响应
    class Result(val realtime: Realtime)
    //表示具体的实时天气数据
    class Realtime(val skycon: String, val temperature: Float, @SerializedName("air_quality") val airQuality: AirQuality)
    //定义Realtime类,包含skycon（天气状况）,temperature（温度）和airQuality（空气质量）三个属性
    class AirQuality(val aqi: AQI)
    //表示空气质量指数
    class AQI(val chn: Float)
    //表示空气质量指数的具体值
}
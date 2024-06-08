package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.* //提供日期等工具类使用

class DailyResponse(val status: String, val result: Result) {
    //包含status和result两个属性,表示未来天气响应
    class Result(val daily: Daily)
    //表示具体的未来天气数据
    class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, @SerializedName("life_index") val lifeIndex: LifeIndex)
    //定义Daily类,包含temperature（温度列表）,skycon（天气状况列表）和lifeIndex（生活指数）三个属性
    class Temperature(val max: Float, val min: Float)
    //定义Temperature类,包含max（最高温度）和min（最低温度）两个属性
    class Skycon(val value: String, val date: Date)
    //定义Skycon类,包含value（天气状况）和date（日期）两个属性
    class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>, val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)
    //定义LifeIndex类,包含四个生活指数列表：coldRisk（感冒指数）,carWashing（洗车指数）,ultraviolet（紫外线指数）和dressing（穿衣指数）
    class LifeDescription(val desc: String)
    //表示生活指数的具体描述
}
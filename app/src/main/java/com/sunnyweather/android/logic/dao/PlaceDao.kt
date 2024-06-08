package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

object PlaceDao { //定义单例对象PlaceDao,用于管理本地数据

    fun savePlace(place: Place) { //定义保存地点信息的方法
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
            //将Place对象转换为JSON字符串并保存到SharedPreferences中
        }
    }

    fun getSavedPlace(): Place { //定义获取已保存地点信息的方法
        val placeJson = sharedPreferences().getString("place", "")
        //从SharedPreferences中获取保存的JSON字符串
        return Gson().fromJson(placeJson, Place::class.java)
        //将JSON字符串解析为Place对象并返回
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}
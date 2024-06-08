package com.sunnyweather.android.ui.weather

import androidx.lifecycle.*
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Location

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()
    //声明一个私有的MutableLiveData对象,用于存储和观察Location数据的变化
    var locationLng = ""
    //存储经度信息
    var locationLat = ""
    //存储纬度信息
    var placeName = ""
    //存储地点名称
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat, placeName)
        //定义一个weatherLiveData变量,通过Transformations.switchMap方法
        //将locationLiveData的数据变化转换为对Repository.refreshWeather方法的调用,获取天气数据
    }

    fun refreshWeather(lng: String, lat: String) {
        //定义一个refreshWeather方法,接受经度lng和纬度lat参数,用于更新locationLiveData
        locationLiveData.value = Location(lng, lat)
        //使用传入的经纬度参数创建一个新的Location对象,并将其赋值给locationLiveData
    }

}
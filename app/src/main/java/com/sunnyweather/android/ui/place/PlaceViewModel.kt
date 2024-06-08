package com.sunnyweather.android.ui.place

import androidx.lifecycle.*
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

class PlaceViewModel : ViewModel() {
//管理与UI相关的数据
    private val searchLiveData = MutableLiveData<String>()
    //观察查询输入变化
    val placeList = ArrayList<Place>()
    //存储地点信息
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
        //根据查询字符串从网络获取地点数据
    }

    fun searchPlaces(query: String) {
        //接收查询字符串 query
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)
    //将 Place 对象保存在本地数据库中
    fun getSavedPlace() = Repository.getSavedPlace()
    //从本地数据库中获取已保存的地点
    fun isPlaceSaved() = Repository.isPlaceSaved()
    //检查是否有地点已保存
}
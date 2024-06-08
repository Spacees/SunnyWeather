package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName //导入Gson库中的SerializedName注解,用于将JSON字段名和Kotlin属性名进行映射,处理JSON解析

data class PlaceResponse(val status: String, val places: List<Place>)
//status: String：请求的状态,places: List<Place>：表示 Place 对象的列表，包含返回的地点信息。
data class Place(val name: String, val location: Location,
                 @SerializedName("formatted_address") val address: String)
//name: String：地点的名称,location: Location：地点的地理位置信息, address: String：地点的格式化地址。
data class Location(val lng: String, val lat: String)
//lng: 经度,lat: 纬度
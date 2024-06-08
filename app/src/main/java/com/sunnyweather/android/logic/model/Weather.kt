package com.sunnyweather.android.logic.model

class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
//定义Weather类,分别表示实时天气数据和未来天气数据
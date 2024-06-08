package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() { //继承自Application类

    companion object {

        const val TOKEN = "oZN3fX9JHryq9m18" // 填入你申请到的令牌值

        @SuppressLint("StaticFieldLeak") //用于忽略将Context设置成静态变量容易产生内存泄漏的警告
        lateinit var context: Context //context变量将在应用开始时被初始化，以便全局访问。
    }

    override fun onCreate() { //重写onCreate方法,负责应用启动时的初始化操作
        super.onCreate()
        context = applicationContext
    }

}
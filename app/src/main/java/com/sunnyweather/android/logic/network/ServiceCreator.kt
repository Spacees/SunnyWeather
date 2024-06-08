package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory //将JSON数据转换为Kotlin/Java对象

object ServiceCreator { //定义一个单例对象

    private const val BASE_URL = "https://api.caiyunapp.com/"
    //定义一个私有常量BASE_URL,存储URL.使用const关键字定义编译时常量
    private val retrofit = Retrofit.Builder() //创建一个私有变量,使用Retrofit.Builder进行初始化
        .baseUrl(BASE_URL) //设置基础 URL
        .addConverterFactory(GsonConverterFactory.create()) //将 JSON 数据转换成 Kotlin/Java 对象
        .build() //调用build()方法构建Retrofit实例

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    //定义泛型函数,接受Class<T>对象作为参数,并返回类型为T的服务实例
    //调用retrofit.create(serviceClass)方法来创建并返回Retrofit接口的实现类
    inline fun <reified T> create(): T = create(T::class.java)
    //定义了一个不带参数的create()方法，并使用inline关键字来修饰方法,使用reified关键字来修饰泛型
}
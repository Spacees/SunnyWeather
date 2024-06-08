package com.sunnyweather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.MainActivity
import com.sunnyweather.android.R
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() { //用于显示地点搜索界面

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }
    //使用懒加载机制获取PlaceViewModel实例
    private lateinit var adapter: PlaceAdapter
    //定义一个延迟初始化的PlaceAdapter变量
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //创建并返回片段视图
        return inflater.inflate(R.layout.fragment_place, container, false)
        //将 fragment_place 布局文件转换为视图并返回
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //在与活动关联之后执行初始化操作
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            //判断如果当前活动是MainActivity并且有已保存的地点
            val place = viewModel.getSavedPlace()
            //获取已保存的地点信息
            val intent = Intent(context, WeatherActivity::class.java).apply {
                //启动WeatherActivity并传递地点信息
                putExtra("location_lng", place.location.lng)
                //添加地理经度信息到Intent中
                putExtra("location_lat", place.location.lat)
                //添加地理纬度信息到Intent中
                putExtra("place_name", place.name)
                //添加地点名称到Intent中
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        //用于RecyclerView的布局管理
        recyclerView.layoutManager = layoutManager
        //将LinearLayoutManager设置为RecyclerView的布局管理器
        adapter = PlaceAdapter(this, viewModel.placeList)
        //传递当前片段实例和地点列表
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener { editable ->
            //为搜索框添加文字变化监听器
            val content = editable.toString()
            //获取当前输入内容
            if (content.isNotEmpty()) {
                //如果输入内容不为空,则执行搜索操作
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                //隐藏RecyclerView
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
                //通知适配器数据已更改,刷新显示
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result ->
            val places = result.getOrNull()
            //获取查询结果中的地点列表,如果查询结果为空返回null
            if (places != null) {
                //判断查询结果
                recyclerView.visibility = View.VISIBLE
                //显示RecyclerView
                bgImageView.visibility = View.GONE
                //隐藏背景图片
                viewModel.placeList.clear()
                //清空地点列表数据
                viewModel.placeList.addAll(places)
                //将新的地点数据添加到列表中
                adapter.notifyDataSetChanged()
                //通知适配器数据已更改，刷新显示
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
                //如果查询过程中发生异常，打印异常堆栈信息
            }
        })
    }

}
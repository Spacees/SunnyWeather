package com.sunnyweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
//适配地点列表视图,接受PlaceFragment和placeList作为参数
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    //用于持有视图组件
        val placeName: TextView = view.findViewById(R.id.placeName)
    //获取布局中的placeName视图组件
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    //获取布局中的placeAddress视图组件
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        //从place_item布局文件中创建视图view
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            //为holder的视图设置点击事件监听器
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                //更新WeatherActivity的经度
                activity.viewModel.locationLat = place.location.lat
                //更新WeatherActivity的纬度
                activity.viewModel.placeName = place.name
                //更新WeatherActivity的地点名称
                activity.refreshWeather()
                //刷新天气信息
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    //添加地理经度信息到Intent中
                    putExtra("location_lat", place.location.lat)
                    //添加地理纬度信息到Intent中
                    putExtra("place_name", place.name)
                    //添加地点名称到Intent中
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place)
            //将点击的地点保存到本地数据库
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //绑定视图数据
        val place = placeList[position]
        //获取当前项的 Place 对象
        holder.placeName.text = place.name
        //设置placeName视图的文本为地点名称
        holder.placeAddress.text = place.address
        //设置placeAddress视图的文本为地点地址
    }

    override fun getItemCount() = placeList.size

}

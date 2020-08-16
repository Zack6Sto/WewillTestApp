package com.example.wewilltestapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wewilltestapp.R
import com.example.wewilltestapp.model.Hourly
import com.squareup.picasso.Picasso
import java.util.*

class TemperatureAdapter (val postList: List<Hourly>, val context: Context?) :
    RecyclerView.Adapter<TemperatureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_temperature,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.temp.text = postList.get(position).temp.toString().substring(0,2)+" ํ"
        var date = getDateTimeFromEpocLongOfSeconds(postList[position].dt.toLong())
        var time = date.toString().substring(11,16)
        holder.date_time.setText("${time}")
        Picasso.get().load("https://openweathermap.org/img/w/"+postList[position].weather[0].icon+".png").into(holder.image)
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val date_time: TextView = view.findViewById(R.id.tv_time)
        val image: ImageView = view.findViewById(R.id.Img_hourly)
        val temp: TextView = view.findViewById(R.id.tv_temp)

    }


    private fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
        try {
            val netDate = Date(epoc*1000)

            return netDate.toString()
        } catch (e: Exception) {
            return e.toString()
        }
    }

}
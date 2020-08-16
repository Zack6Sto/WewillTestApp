package com.example.wewilltestapp

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wewilltestapp.adapter.TemperatureAdapter
import com.example.wewilltestapp.model.Temperature
import com.example.wewilltestapp.presenter.PresentenData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var mPresentenData = PresentenData()
    lateinit var mAdapter :TemperatureAdapter
    private var CODE = 123

    private lateinit var lastLocation: Location
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat :Double? = null
    private  var lon :Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setLatLon()

    }

    private fun setApi(latdata: Double, londata: Double) {

        mPresentenData.GetAllPersenterRx(latdata,londata,"daily","metric","69aa82683bee462c0a2eae9c82a4063c",this::Next,this::Error)
    }
    private fun Error(message:String) {
    }

    private fun Next(Temperature : Temperature) {
        tv_city.setText(Temperature.timezone)
        temperature.setText(Temperature.current.temp.toString().substring(0,2)+" ํ")
        Picasso.get().load("https://openweathermap.org/img/w/"+Temperature.current.weather[0].icon+".png").into(imageView)
        tv_fewClouds.setText(Temperature.current.weather[0].description)
        tv_humidity.setText(Temperature.current.humidity.toString()+"% \nHumidity")
        tv_wind.setText(Temperature.current.wind_speed.toString()+"%\nWind speed")

        var date = getDateTimeFromEpocLongOfSeconds(Temperature.current.dt.toLong())
        var day = date.toString().substring(8,10)
        var Month = date.toString().substring(4,7)
        var Year = date.toString().substring(30)
        var time = date.toString().substring(11,16)
        tv_date.setText("${day} ${Month} ${Year},${time} ")

        mAdapter = TemperatureAdapter(
            Temperature.hourly,
            this
        )
        rcv_list.apply {
            layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter  =  mAdapter
            mAdapter.notifyDataSetChanged()
        }


    }

    private fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
        try {
            val netDate = Date(epoc*1000)

            return netDate.toString()
        } catch (e: Exception) {
            return e.toString()
        }
    }
    private fun setLatLon() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lastLocation = location
                Log.d("Data",location.toString())
                setApi(location.latitude,location.longitude)
            }
        }
    }
}
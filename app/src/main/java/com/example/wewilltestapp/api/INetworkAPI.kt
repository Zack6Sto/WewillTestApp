package com.example.wewilltestapp.api

import com.example.wewilltestapp.model.Temperature
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkAPI {

    @GET("/data/2.5/onecall?")
    fun getAllPosts(@Query("lat")lat:Double,
                    @Query("lon")lon:Double,
                    @Query("exclude")exclude:String,
                    @Query("units")units:String,
                    @Query("appid")appid:String
    ): Observable<Temperature>
}

package com.example.wewilltestapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataModule {
    private var retrofit: Retrofit? = null
    private  var httpClient: OkHttpClient.Builder? = null
    private var BasUrl = "https://api.openweathermap.org"

    private fun getInstance(): Retrofit {
        if (retrofit == null){
            // Okhttp เพื่อเซื่อมต่อกับสิ่งที่เป็น HTTP
            httpClient = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
            //เรียกใช้ retrofit + rxjava2 เพื่อเซื่อม API
            retrofit = Retrofit.Builder()
                .baseUrl(BasUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient!!.build())
                .build()

        }
        return  retrofit as Retrofit
    }
    val mHttpRetrofit = getInstance()

    //เซื่อมต่อ retrofit และ serviceAPI
    val myAppService = mHttpRetrofit.create(
        INetworkAPI::class.java)

}
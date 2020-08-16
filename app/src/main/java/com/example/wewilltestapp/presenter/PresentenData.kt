package com.example.wewilltestapp.presenter

import android.util.Log
import com.example.wewilltestapp.api.DataModule
import com.example.wewilltestapp.model.Temperature
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresentenData {
    var mDisposable: Disposable? = null

    fun GetAllPersenterRx(
        lat:Double, lon:Double, exclude:String, units:String, appid:String,
        dataResponse : (Temperature) -> Unit,
        MessageError: (String)-> Unit
    ){

        mDisposable = DataModule.myAppService.getAllPosts(lat, lon, exclude, units, appid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Temperature>(){
                override fun onComplete() {

                }

                override fun onNext(response: Temperature) {
                    Log.d("Data", response.toString())
                    dataResponse.invoke(response)
                }

                override fun onError(e: Throwable) {
                    Log.d("Data",e.toString())
                    MessageError.invoke(e.message!!)
                }

            })

    }
}
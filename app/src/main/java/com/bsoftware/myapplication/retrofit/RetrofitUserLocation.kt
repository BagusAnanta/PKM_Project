package com.bsoftware.myapplication.retrofit

import com.bsoftware.myapplication.apideclaration.UserLocationAPIInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUserLocation {
    val instanceUserLocation : UserLocationAPIInterface by lazy {
        val retrofitUserLocationInit = Retrofit.Builder()
            .baseUrl("http://192.168.100.11/Siduka/UserLocation/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitUserLocationInit.create(UserLocationAPIInterface::class.java)
    }
}
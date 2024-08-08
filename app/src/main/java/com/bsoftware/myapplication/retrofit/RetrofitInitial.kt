package com.bsoftware.myapplication.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitial {
    val retrofitInit = Retrofit.Builder()
        .baseUrl("Url At here")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
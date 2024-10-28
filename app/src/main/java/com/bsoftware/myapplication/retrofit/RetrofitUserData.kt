package com.bsoftware.myapplication.retrofit

import com.bsoftware.myapplication.apideclaration.UserDataAPIInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUserData {
    val instanceUserData : UserDataAPIInterface by lazy {
        val retrofitUserDataInit =  Retrofit.Builder()
            .baseUrl("https://api.kpadsumsel.or.id/UserData/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitUserDataInit.create(UserDataAPIInterface::class.java)
    }
}
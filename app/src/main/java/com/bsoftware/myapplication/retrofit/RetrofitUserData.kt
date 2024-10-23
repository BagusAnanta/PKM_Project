package com.bsoftware.myapplication.retrofit

import com.bsoftware.myapplication.apideclaration.UserDataAPIInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUserData {
    val instanceUserData : UserDataAPIInterface by lazy {
        val retrofitUserDataInit =  Retrofit.Builder()
            .baseUrl("http://192.168.100.11/Siduka/UserData/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitUserDataInit.create(UserDataAPIInterface::class.java)
    }
}
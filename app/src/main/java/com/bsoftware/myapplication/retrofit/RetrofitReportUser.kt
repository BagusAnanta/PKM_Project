package com.bsoftware.myapplication.retrofit

import com.bsoftware.myapplication.apideclaration.UserReportAPIInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitReportUser {
    val instanceUserReport : UserReportAPIInterface by lazy{
        val retrofitReportUser = Retrofit.Builder()
            .baseUrl("http://192.168.100.109/Siduka/ReportUser/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitReportUser.create(UserReportAPIInterface::class.java)
    }
}
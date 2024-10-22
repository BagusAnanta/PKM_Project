package com.bsoftware.myapplication.retrofit

import com.bsoftware.myapplication.apideclaration.UserReportAPIInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitReportUser {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val instanceUserReport : UserReportAPIInterface by lazy{
        val retrofitReportUser = Retrofit.Builder()
            .baseUrl("http://192.168.100.11/Siduka/ReportUser/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitReportUser.create(UserReportAPIInterface::class.java)
    }
}
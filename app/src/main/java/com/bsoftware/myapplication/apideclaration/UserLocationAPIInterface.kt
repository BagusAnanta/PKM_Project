package com.bsoftware.myapplication.apideclaration

import com.bsoftware.myapplication.dataclass.CreateLocationDataClass
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserLocationAPIInterface {
    @GET("ReadUserLocation.php")
    fun getUserLocation(): Call<CreateLocationDataClass>

    @FormUrlEncoded
    @POST("CreateUserLocation.php")
    fun createDataLocation(
        @Field("uidUser") uidUser: String = "",
        @Field("longitude") longitude: String = "",
        @Field("latitude") latitude: String = "",
        @Field("address") address: String = ""
    ) : Call<CreateLocationDataClass>

    @FormUrlEncoded
    @POST("UpdateUserLocation.php")
    fun updateDataLocation(
        @Field("uidUser") uidUser: String = "",
        @Field("longitude") longitude: String = "",
        @Field("latitude") latitude: String = "",
        @Field("address") address: String = ""
    ) : Call<CreateLocationDataClass>

    @POST("DeleteUserLocation.php")
    fun deleteDataLocation(
        @Field("uidUser") uidUser: String = ""
    ) : Call<CreateLocationDataClass>

}
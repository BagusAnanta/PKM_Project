package com.bsoftware.myapplication.apideclaration

import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserDataAPIInterface {

    // read more data from server
    @FormUrlEncoded
    @POST("ReadUserData.php")
    fun getDataUser(
        @Field("email") email : String = ""
    ): Call<CreateUserDataClass>

    // read more data from server using ID
    @GET("ReadUserDataByUid.php")
    fun getDataUserById(
        @Field("uidUser") uidUser : String
    ) : Call<CreateUserDataClass>

    @FormUrlEncoded
    @POST("CreateUserData.php")
    fun createDataUser(
        @Field("uidUser") uidUser : String = "",
        @Field("fullname") fullname : String = "",
        @Field("idNumber") idNumber : String = "",
        @Field("address") address : String = "",
        @Field("phoneNumber") phoneNumber : String = "",
        @Field("email") email : String = "",
        @Field("birthday") birthday : String = "",
        @Field("sex") sex : String = "",
        @Field("password") password : String = ""
    ) : Call<CreateUserDataClass>

    @FormUrlEncoded
    @POST("UpdateUserData.php")
    fun updateDataUser(
        @Field("uidUser") uidUser : String = "",
        @Field("fullname") fullname : String = "",
        @Field("idNumber") idNumber : String = "",
        @Field("address") address : String = "",
        @Field("phoneNumber") phoneNumber : String = "",
        @Field("email") email : String = "",
        @Field("birthday") birthday : String = "",
        @Field("sex") sex : String = "",
        @Field("password") password : String = ""
    ) : Call<CreateUserDataClass>

    @POST("DeleteUserData.php")
    fun deleteDataUser(
        @Field("uidUser") uidUser : String
    ) : Call<CreateUserDataClass>

}
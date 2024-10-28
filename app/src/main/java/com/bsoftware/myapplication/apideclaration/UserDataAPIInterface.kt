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
        @Field("id") id : String
    ) : Call<CreateUserDataClass>

    @FormUrlEncoded
    @POST("CreateUserData.php")
    fun createDataUser(
        @Field("id") id : String = "",
        @Field("name") name : String = "",
        @Field("email") email : String = "",
        @Field("nohp") nohp : String = "",
        @Field("alamat") alamat : String = "",
        @Field("verified_at") verified_at : String = "",
        @Field("token") token : String = "",
        @Field("is_admin") is_admin : String = "",
        @Field("password") password : String = "",
        @Field("remember_token") remember_token : String = "",
        @Field("created_at") created_at : String = "",
        @Field("updated_at") updated_at : String = ""
    ) : Call<CreateUserDataClass>

    @FormUrlEncoded
    @POST("UpdateUserData.php")
    fun updateDataUser(
        @Field("id") id : String = "",
        @Field("name") name : String = "",
        @Field("email") email : String = "",
        @Field("nohp") nohp : String = "",
        @Field("alamat") alamat : String = "",
        @Field("verified_at") verified_at : String = "",
        @Field("token") token : String = "",
        @Field("is_admin") is_admin : String = "",
        @Field("password") password : String = "",
        @Field("remember_token") remember_token : String = "",
        @Field("created_at") created_at : String = "",
        @Field("updated_at") updated_at : String = ""
    ) : Call<CreateUserDataClass>

    @POST("DeleteUserData.php")
    fun deleteDataUser(
        @Field("uidUser") uidUser : String
    ) : Call<CreateUserDataClass>

}
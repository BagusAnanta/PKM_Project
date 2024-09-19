package com.bsoftware.myapplication.apideclaration

import com.bsoftware.myapplication.dataclass.CreateReportUserDataClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserReportAPIInterface {
    @GET("ReadReportUser.php")
    fun getDataReportUser(): Call<CreateReportUserDataClass>

    @Multipart
    @POST("CreateReportUser.php")
    fun createReportUser(
        @Part("uidUser") uidUser: RequestBody,
        @Part("description") description: RequestBody,
        @Part("reportDate") reportDate: RequestBody,
        @Part image : MultipartBody.Part
    ) : Call<CreateReportUserDataClass>

    @FormUrlEncoded
    @POST("UpdateReportUser.php")
    fun updateReportUser(
        @Field("idReport") idReport: String = "",
        @Field("description") description: String = "",
        @Field("reportDate") reportDate: String = "",
        @Field("imageUrl") imageUrl : String = ""
    ) : Call<CreateReportUserDataClass>

    @FormUrlEncoded
    @POST("DeleteReportUser.php")
    fun deleteReportUser(
        @Field("idReport") idReport: String = ""
    ) : Call<CreateReportUserDataClass>

}
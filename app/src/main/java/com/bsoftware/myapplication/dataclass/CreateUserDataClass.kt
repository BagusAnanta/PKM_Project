package com.bsoftware.myapplication.dataclass

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CreateUserDataClass(
    @SerializedName("status") var status : String = "",
    @SerializedName("statusCode") var statusCode : String = "",
    @SerializedName("data") var data : List<UserData> = emptyList(),
    @SerializedName("message") var message : String = "",
) : java.io.Serializable

data class UserData(
    @SerializedName("id") var id : String = "",
    @SerializedName("name") var name : String = "",
    @SerializedName("email") var email : String = "",
    @SerializedName("nohp") var nohp : String = "",
    @SerializedName("alamat") var alamat : String = "",
    @SerializedName("verified_at") var verified_at : String = "",
    @SerializedName("token") var token : String = "",
    @SerializedName("is_admin") var is_admin : String = "",
    @SerializedName("password") var password : String = "",
    @SerializedName("remember_token") var remember_token : String = "",
    @SerializedName("created_at") var created_at : String = "",
    @SerializedName("updated_at") var updated_at : String = ""
)

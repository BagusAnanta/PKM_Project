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
    @SerializedName("uidUser") var uidUser : String = "",
    @SerializedName("fullname") var fullname : String = "",
    @SerializedName("idNumber") var idNumber : String = "",
    @SerializedName("address") var address : String = "",
    @SerializedName("phoneNumber") var phoneNumber : String = "",
    @SerializedName("email") var email : String = "",
    @SerializedName("birthday") var birthday : String = "",
    @SerializedName("sex") var sex : String = "",
    @SerializedName("password") var password : String = ""
)

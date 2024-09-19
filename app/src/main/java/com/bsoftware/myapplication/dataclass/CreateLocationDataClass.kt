package com.bsoftware.myapplication.dataclass

import com.google.gson.annotations.SerializedName

// default data fill 0.0
// longitude and latitude data
data class CreateLocationDataClass(
    @SerializedName("status") var status : String = "",
    @SerializedName("statusCode") var statusCode : String = "",
    @SerializedName("data") var data : List<LocationData> = emptyList(),
    @SerializedName("message") var message : String = ""
) : java.io.Serializable

data class LocationData(
    @SerializedName("uidUser") var uidUser: String = "",
    @SerializedName("longitude") var longitude: String = "",
    @SerializedName("latitude") var latitude: String = "",
    @SerializedName("address") var address: String = ""
)
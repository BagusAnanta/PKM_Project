package com.bsoftware.myapplication.dataclass

import com.google.gson.annotations.SerializedName

data class CreateReportUserDataClass(
    var status : String = "",
    var statusCode : String = "",
    var data : ReportData?,
    var message : String = "",
) : java.io.Serializable

data class ReportData(
    val uidUser : String = "",
    val description : String = "",
    val reportDate : String = "",
    val imageUrl : String = ""
)

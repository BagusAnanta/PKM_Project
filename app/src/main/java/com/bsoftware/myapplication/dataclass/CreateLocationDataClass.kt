package com.bsoftware.myapplication.dataclass

// default data fill 0.0
// longitude and latitude data
data class CreateLocationDataClass(
    val locationData : List<Double> = arrayListOf(0.00,0.00),
    val locationAddress : String = "",
)
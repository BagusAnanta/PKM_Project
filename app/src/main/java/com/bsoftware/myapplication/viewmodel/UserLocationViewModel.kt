package com.bsoftware.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsoftware.myapplication.dataclass.CreateLocationDataClass
import com.bsoftware.myapplication.dataclass.CreateStatusDataClass
import com.bsoftware.myapplication.dataclass.LocationData
import com.bsoftware.myapplication.retrofit.RetrofitUserLocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserLocationViewModel : ViewModel(){
    private val _response = MutableLiveData<List<CreateStatusDataClass>>()
    val response : LiveData<List<CreateStatusDataClass>> get() = _response

    private val _userLocationData = MutableLiveData<List<LocationData>>()
    val userLocationData : LiveData<List<LocationData>> get() = _userLocationData

    fun readUserLocation(){
        try{
            RetrofitUserLocation.instanceUserLocation.getUserLocation().enqueue(object : Callback<CreateLocationDataClass>{
                override fun onResponse(
                    call: Call<CreateLocationDataClass>,
                    response: Response<CreateLocationDataClass>
                ) {
                    _userLocationData.value = response.body()?.data
                    _response.value = listOf(
                        CreateStatusDataClass(
                            response.body()?.status ?: "",
                            response.body()?.statusCode ?: "",
                            response.body()?.message ?: "",
                            response.body()?.data.toString()
                        )
                    )
                }

                override fun onFailure(call: Call<CreateLocationDataClass>, t: Throwable) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            "error",
                            "500",
                            t.message ?: "",
                            ""
                        )
                    )
                }

            })
        } catch (e : Exception){
            Log.e("Read UserLocation Error: ", e.toString())
        }
    }

    fun createUserLocation(uidUser : String,longitude : String, latitude : String, address : String){
        try{
            RetrofitUserLocation.instanceUserLocation.createDataLocation(uidUser, longitude, latitude, address).enqueue(object : Callback<CreateLocationDataClass>{
                override fun onResponse(
                    call: Call<CreateLocationDataClass>,
                    response: Response<CreateLocationDataClass>
                ) {
                   if(response.isSuccessful){
                       val apiResponse = response.body()

                       if(apiResponse?.status == "error" && apiResponse.statusCode == "400"){
                           _response.value = listOf(
                               CreateStatusDataClass(
                                   apiResponse.status,
                                   apiResponse.statusCode,
                                   apiResponse.message,
                                   apiResponse.data.toString()
                               )
                           )
                       } else if (apiResponse?.status == "success" && apiResponse.statusCode == "201"){
                           _response.value = listOf(
                               CreateStatusDataClass(
                                   apiResponse.status,
                                   apiResponse.statusCode,
                                   apiResponse.message,
                                   apiResponse.data.toString()
                               )
                           )
                       } else if (apiResponse?.status == "error" && apiResponse.statusCode == "500"){
                           _response.value = listOf(
                               CreateStatusDataClass(
                                   apiResponse.status,
                                   apiResponse.statusCode,
                                   apiResponse.message,
                                   apiResponse.data.toString()
                               )
                           )
                       } else {
                           _response.value = listOf(
                               CreateStatusDataClass(
                                   apiResponse?.status ?: "",
                                   apiResponse?.statusCode ?: "",
                                   apiResponse?.message ?: "",
                                   apiResponse?.data.toString()
                               )
                           )
                       }


                   }
                }

                override fun onFailure(call: Call<CreateLocationDataClass>, t: Throwable) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            "error",
                            "500",
                            t.message ?: "",
                            ""
                        )
                    )
                }

            })
        } catch (e : Exception){
            Log.e("Create UserLocation Error: ", e.toString())
        }
    }

    fun updateUserLocation(uidUser : String,longitude : String, latitude : String, address : String){
        try{
            RetrofitUserLocation.instanceUserLocation.updateDataLocation(uidUser, longitude, latitude, address).enqueue(object : Callback<CreateLocationDataClass>{
                override fun onResponse(
                    call: Call<CreateLocationDataClass>,
                    response: Response<CreateLocationDataClass>
                ) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            response.body()?.status ?: "",
                            response.body()?.statusCode ?: "",
                            response.body()?.message ?: "",
                            response.body()?.data.toString()
                        )
                    )
                }

                override fun onFailure(call: Call<CreateLocationDataClass>, t: Throwable) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            "error",
                            "500",
                            t.message ?: "",
                            ""
                        )
                    )
                }

            })
        } catch (e : Exception){
            Log.e("Update UserLocation Error: ", e.toString())
        }
    }

    fun deleteUserLocation(uidUser : String){
        try{
            RetrofitUserLocation.instanceUserLocation.deleteDataLocation(uidUser).enqueue(object : Callback<CreateLocationDataClass>{
                override fun onResponse(
                    call: Call<CreateLocationDataClass>,
                    response: Response<CreateLocationDataClass>
                ) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            response.body()?.status ?: "",
                            response.body()?.statusCode ?: "",
                            response.body()?.message ?: "",
                            response.body()?.data.toString()
                        )
                    )
                }

                override fun onFailure(call: Call<CreateLocationDataClass>, t: Throwable) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            "error",
                            "500",
                            t.message ?: "",
                            ""
                        )
                    )
                }

            })
        } catch (e : Exception){
            Log.e("Delete UserLocation Error: ", e.toString())
        }
    }
}
package com.bsoftware.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsoftware.myapplication.dataclass.CreateReportUserDataClass
import com.bsoftware.myapplication.dataclass.CreateStatusDataClass
import com.bsoftware.myapplication.dataclass.ReportData
import com.bsoftware.myapplication.retrofit.RetrofitReportUser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserReportViewModel : ViewModel() {
    private val _response = MutableLiveData<List<CreateStatusDataClass>>()
    val response : LiveData<List<CreateStatusDataClass>> get() = _response

    private val _reportData = MutableLiveData<List<ReportData>>()
    val reportData : LiveData<List<ReportData>> get() = _reportData

   /* fun readReportData(){
        try{
            RetrofitReportUser.instanceUserReport.getDataReportUser().enqueue(object : Callback<CreateReportUserDataClass> {
                override fun onResponse(
                    call: Call<CreateReportUserDataClass>,
                    response: Response<CreateReportUserDataClass>
                ) {
                    _reportData.value = response.body()?.data
                    _response.value = listOf(
                        CreateStatusDataClass(
                            response.body()?.status ?: "",
                            response.body()?.statusCode ?: "",
                            response.body()?.message ?: "",
                            response.body()?.data.toString()
                        )
                    )
                }

                override fun onFailure(call: Call<CreateReportUserDataClass>, t: Throwable) {
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
            Log.e("Read UserReport Error: ", e.toString())
        }
    }*/

    fun createReportUser(uidUser : String, description : String, reportDate : String, imageFile : File){
        try{
            val uidRequest = uidUser.toRequestBody("text/plain".toMediaType())
            val descriptionRequest = description.toRequestBody("text/plain".toMediaType())
            val reportDateRequest = reportDate.toRequestBody("text/plain".toMediaType())

            val requestFile = imageFile.asRequestBody("image/*".toMediaType())
            val imageRequest = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            RetrofitReportUser.instanceUserReport.createReportUser(uidRequest, descriptionRequest, reportDateRequest, imageRequest).enqueue(object : Callback<CreateReportUserDataClass>{
                override fun onResponse(
                    call: Call<CreateReportUserDataClass>,
                    response: Response<CreateReportUserDataClass>
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
                        } else if(apiResponse?.status == "success" && apiResponse.statusCode == "201"){
                            _response.value = listOf(
                                CreateStatusDataClass(
                                    apiResponse.status,
                                    apiResponse.statusCode,
                                    apiResponse.message,
                                    apiResponse.data.toString()
                                )
                            )
                        } else if(apiResponse?.status == "error" && apiResponse.statusCode == "500"){
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

                override fun onFailure(call: Call<CreateReportUserDataClass>, t: Throwable) {
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
            Log.e("Create Report User Error: ", e.toString())
        }
    }

    /*fun updateReportUser(uidUser : String, description : String, reportDate : String, imageUrl : String){
        try{
            RetrofitReportUser.instanceUserReport.updateReportUser(uidUser, description, reportDate, imageUrl).enqueue(object : Callback<CreateReportUserDataClass>{
                override fun onResponse(
                    call: Call<CreateReportUserDataClass>,
                    response: Response<CreateReportUserDataClass>
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

                override fun onFailure(call: Call<CreateReportUserDataClass>, t: Throwable) {
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
            Log.e("Update Report User Error: ", e.toString())
        }
    }*/

   /* fun deleteReportUser(uidUser : String){
        try{
            RetrofitReportUser.instanceUserReport.deleteReportUser(uidUser).enqueue(object : Callback<CreateReportUserDataClass>{
                override fun onResponse(
                    call: Call<CreateReportUserDataClass>,
                    response: Response<CreateReportUserDataClass>
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

                override fun onFailure(call: Call<CreateReportUserDataClass>, t: Throwable) {
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
            Log.e("Delete Report User Error: ", e.toString())
        }
    }*/
}
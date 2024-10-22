package com.bsoftware.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsoftware.myapplication.dataclass.CreateReportUserDataClass
import com.bsoftware.myapplication.dataclass.CreateStatusDataClass
import com.bsoftware.myapplication.dataclass.ReportData
import com.bsoftware.myapplication.retrofit.RetrofitReportUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.SocketException

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
            val imageRequest = MultipartBody.Part.createFormData("photo", imageFile.name, requestFile)

            RetrofitReportUser.instanceUserReport.createReportUser(uidRequest, descriptionRequest, reportDateRequest, imageRequest)
                .enqueue(object : Callback<CreateReportUserDataClass>{
                    override fun onResponse(
                        call: Call<CreateReportUserDataClass>,
                        response: Response<CreateReportUserDataClass>
                    ) {
                        Log.d("Create Report User Response Success: ", response.toString())

                        if(response.isSuccessful && response.body()?.status == "success"){
                            Log.d("Create Report User Response Success: ", response.body().toString())
                            _response.value = listOf(
                                CreateStatusDataClass(
                                    response.body()?.status ?: "",
                                    response.body()?.statusCode ?: "",
                                    response.body()?.message ?: "",
                                    response.body()?.data.toString()
                                )
                            )
                        } else {
                            Log.e("Create Report User Response Error: ", response.body().toString())
                            _response.value = listOf(
                                CreateStatusDataClass(
                                    response.body()?.status ?: "",
                                    response.body()?.statusCode ?: "",
                                    response.body()?.message ?: "",
                                    response.body()?.data.toString()
                                )
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<CreateReportUserDataClass>,
                        t: Throwable
                    ) {
                        Log.e("Create Report User Response Error", "Call API fail : ${t.message}")
                    }

                })

        } catch (e : Exception){
            Log.e("Exception Create Report User Error: ", e.toString())
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
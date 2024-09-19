package com.bsoftware.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsoftware.myapplication.apideclaration.UserDataAPIInterface
import com.bsoftware.myapplication.dataclass.CreateStatusDataClass
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.dataclass.UserData
import com.bsoftware.myapplication.retrofit.RetrofitUserData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataViewModel : ViewModel() {
    private val _response = MutableLiveData<List<CreateStatusDataClass>>()
    val response : LiveData<List<CreateStatusDataClass>> get() = _response

    private val _userData = MutableLiveData<List<UserData>>()
    val userData: LiveData<List<UserData>> get() = _userData

    // get data from api
    fun readUserData(email : String){
        try{
            RetrofitUserData.instanceUserData.getDataUser(email).enqueue(object : Callback<CreateUserDataClass>{
                override fun onResponse(
                    call: Call<CreateUserDataClass>,
                    response: Response<CreateUserDataClass>
                ) {
                    _userData.value = response.body()?.data
                    _response.value = listOf(
                        CreateStatusDataClass(
                            response.body()?.status ?: "",
                            response.body()?.statusCode ?: "",
                            response.body()?.message ?: "",
                            response.body()?.data.toString()
                        )
                    )

                    Log.d("Response readUserData", response.body()?.data.toString())
                    Log.d("Response readUserData Feedback", _response.value.toString())
                }

                override fun onFailure(call: Call<CreateUserDataClass>, t: Throwable) {
                    _response.value = listOf(
                        CreateStatusDataClass(
                            "error",
                            "500",
                            t.message ?: "",
                            ""
                        )
                    )

                    Log.e("Response readUserData Feedback OnFailure", _response.value.toString())
                }

            })
        } catch (e : Exception){
            Log.e("Read UserData Error: ", e.toString())
        }
    }

    fun readUserDataByID(uid : String){
        try{
            RetrofitUserData.instanceUserData.getDataUserById(uid).enqueue(object : Callback<CreateUserDataClass>{
                override fun onResponse(
                    call: Call<CreateUserDataClass>,
                    response: Response<CreateUserDataClass>
                ) {
                    _userData.value = response.body()?.data
                    _response.value = listOf(
                        CreateStatusDataClass(
                            response.body()?.status ?: "",
                            response.body()?.statusCode ?: "",
                            response.body()?.message ?: "",
                            response.body()?.data.toString()
                        )
                    )
                }

                override fun onFailure(call: Call<CreateUserDataClass>, t: Throwable) {
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
            Log.e("Read UserData Error ByID: ", e.toString())
        }
    }

    fun createUserData(uidUser : String, fullname : String, idNumber : String, address : String, phoneNumber : String, email : String, birthday : String, sex : String, password : String){
        try{
            RetrofitUserData.instanceUserData.createDataUser(uidUser, fullname, idNumber, address, phoneNumber, email, birthday, sex, password).enqueue(object : Callback<CreateUserDataClass> {
                override fun onResponse(
                    call: Call<CreateUserDataClass>,
                    response: Response<CreateUserDataClass>
                ) {
                    if(response.isSuccessful){
                        val apiResponse = response.body()

                        if(apiResponse?.status == "success" && apiResponse.statusCode == "201"){
                            // indicate a data success for create, create a message at here
                            _response.value = listOf(
                                CreateStatusDataClass(
                                    apiResponse.status,
                                    apiResponse.statusCode,
                                    apiResponse.message,
                                    apiResponse.data.toString()
                                )
                            )
                        } else if(apiResponse?.status == "error" && apiResponse.statusCode == "400"){
                            // indicate a data failed for create or have field missing
                            _response.value = listOf(
                                CreateStatusDataClass(
                                    apiResponse.status,
                                    apiResponse.statusCode,
                                    apiResponse.message,
                                    apiResponse.data.toString()
                                )
                            )
                        } else if(apiResponse?.status == "error" && apiResponse.statusCode == "500"){
                            // indicate a data failed for create
                            _response.value = listOf(
                                CreateStatusDataClass(
                                    apiResponse.status,
                                    apiResponse.statusCode,
                                    apiResponse.message,
                                    apiResponse.data.toString()
                                )
                            )
                        } else {
                            // indicate a uknown error
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

                override fun onFailure(call: Call<CreateUserDataClass>, t: Throwable) {
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
            Log.e("Create UserData Error :", e.toString())
        }
    }

    fun updateUserData(uidUser : String, fullname : String, idNumber : String, address : String, phoneNumber : String, email : String, birthday : String, sex : String, password : String) {
       try {
           RetrofitUserData.instanceUserData.updateDataUser(uidUser, fullname, idNumber, address, phoneNumber, email, birthday, sex, password).enqueue(object : Callback<CreateUserDataClass> {
               override fun onResponse(
                   call: Call<CreateUserDataClass>,
                   response: Response<CreateUserDataClass>
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

               override fun onFailure(call: Call<CreateUserDataClass>, t: Throwable) {
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
           Log.e("Update UserData Error :", e.toString())
       }
    }

    fun deleteUserData(uid : String){
       try {
           RetrofitUserData.instanceUserData.deleteDataUser(uid).enqueue(object : Callback<CreateUserDataClass> {
               override fun onResponse(
                   call: Call<CreateUserDataClass>,
                   response: Response<CreateUserDataClass>
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

               override fun onFailure(call: Call<CreateUserDataClass>, t: Throwable) {
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
           Log.e("Delete UserData Error :", e.toString())
       }
    }

}
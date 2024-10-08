package com.bsoftware.myapplication.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.preferencedatastore.UserDataDatastore
import com.bsoftware.myapplication.sharepref.UidSharePref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseAuthentication {

    var uidUserAuth : String? = null
        get() = field
        set(value) {
            field = value
        }

    private val firstChild = "UserData"
        get() = field

    // init firebase
    private fun initFirebaseAuth() : FirebaseAuth{
        return Firebase.auth
    }

    fun initFirebaseRealtime() : DatabaseReference{
        return Firebase.database.reference
    }

    fun createDataUser(
        userData : CreateUserDataClass,
        emailPassword : String,
        onSuccess : () -> Unit = {},
        onFailed : () -> Unit = {},
        activity : Activity,
        context: Context
    ){
       /* initFirebaseAuth().createUserWithEmailAndPassword(userData.email,emailPassword)
            .addOnCompleteListener(activity) {task ->
                if(task.isSuccessful){
                    // in here, we gonna save or write DataCheckOut to into realtime database
                    // but first, we must get user UID for unique user identifier
                    userData.uidUser = initFirebaseAuth().uid.toString()
                    initFirebaseRealtime().child(firstChild).child(userData.uidUser).setValue(userData)
                        .addOnSuccessListener {msg ->
                            Log.d("OnDataUserSaver","Data User Save Successfull : $msg")
                            // in here, we gonna get a data
                            uidUserAuth = initFirebaseAuth().uid
                            CoroutineScope(Dispatchers.IO).launch {
                                UidSharePref(activity).setUidPreference(context,uidUserAuth.toString())
                            }
                            getUserInformationUseUid(uidUserAuth,context)
                        }
                        .addOnFailureListener {
                            Log.d("OnDataUserSaver", "Data User Fail To Save")
                        }

                    // after save in realtime database, go to more activity
                    onSuccess()
                }

                onFailed()
                val exception = task.exception
                Log.e("CreateDataUser() Exception :", exception.toString())
            }*/
    }

    fun loginUser(
        email : String,
        password : String,
        activity: Activity,
        onSuccess: () -> Unit = {},
        onFailed: () -> Unit = {},
        context: Context,
    ) : String{
        var message = "Default Message"
        initFirebaseAuth().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity) {task ->

                if(task.isSuccessful){
                    // in here we get a data user from UID
                    // get data for use uid
                    getUserInformationUseUid(initFirebaseAuth().uid,context)
                    CoroutineScope(Dispatchers.IO).launch {
                        UidSharePref(activity).setUidPreference(context,initFirebaseAuth().uid.toString())
                    }
                    onSuccess()
                    message = "Login Success"
                } else {
                    val exception = task.exception
                    if(exception is FirebaseAuthInvalidCredentialsException){
                        when(exception.errorCode){
                            "ERROR_INVALID_EMAIL" -> {
                                Log.e("Invalid Email","Invalid User Email")
                                message = "Invalid User Email"
                            }

                            "ERROR_WRONG_PASSWORD" -> {
                                Log.e("Wrong Password","Wrong Password")
                                message = "Wrong Password"
                            }

                            "ERROR_USER_NOT_FOUND" -> {
                                Log.e("User Not Found","User Not Found")
                                message = "User Not Found"
                            }

                            else -> {
                                Log.e("Login Error","Login Error")
                                message = "Login Error, please contact developer"
                            }
                        }
                    } else {
                        Log.e("Login Error","Login Error")
                        message = "Login Error, please contact developer"
                    }

                    // fail function will be executed
                    onFailed()
                }

            }

            return message
            /*.addOnFailureListener {e ->
                Log.e("loginUser() Error", "loginUser() Error At : ${e.stackTraceToString()}")
                onFailed()
            }*/
    }


    /*fun checkUserLogin(onLogin : () -> Unit, onFailLogin : () -> Unit){
        val user = Firebase.auth.currentUser

        if(user != null){
            // if user its have and not null or sign in
            // in here, we gonna get a user data from firebase
            uidUserAuth = user.uid
            onLogin()
        } else {
            onFailLogin()
        }
    }*/

    private fun getUserInformationUseUid(uid : String?,context : Context){
        val uidUserData = initFirebaseRealtime().child("UserData").child(uid.toString())

        uidUserData.get().addOnCompleteListener {task ->
            if(task.isSuccessful){
                val getDataUser = task.result.getValue(CreateUserDataClass::class.java)
                /*if (getDataUser != null) {
                    Log.d("Uid", getDataUser.uidUser)
                    Log.d("Name", getDataUser.fullname)
                    Log.d("IdNum", getDataUser.idNumber)
                    Log.d("Address", getDataUser.address)
                    Log.d("PhoneNum", getDataUser.phoneNumber)
                    Log.d("Email", getDataUser.email)
                    Log.d("Birthday", getDataUser.birthday)
                    Log.d("Sex", getDataUser.sex)

                    // in here, we gonna safe a data into preference DataStorage
                    CoroutineScope(Dispatchers.IO).launch {
                        UserDataDatastore(context).storeUserDataProfile(
                            CreateUserDataClass(
                                getDataUser.uidUser,
                                getDataUser.fullname,
                                getDataUser.idNumber,
                                getDataUser.address,
                                getDataUser.phoneNumber,
                                getDataUser.email,
                                getDataUser.birthday,
                                getDataUser.sex
                            )
                        )
                    }
                }*/
            }
        }
    }
}
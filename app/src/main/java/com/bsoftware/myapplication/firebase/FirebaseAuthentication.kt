package com.bsoftware.myapplication.firebase

import android.app.Activity
import android.util.Log
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database

class FirebaseAuthentication {

    // init firebase
    private fun initFirebaseAuth() : FirebaseAuth{
        return Firebase.auth
    }

    private fun initFirebaseRealtimeUserData() : DatabaseReference{
        return Firebase.database.reference
    }

    fun createDataUser(
        userData : CreateUserDataClass,
        emailPassword : String,
        onSuccess : () -> Unit = {},
        onFailed : () -> Unit = {},
        activity : Activity
    ){
        initFirebaseAuth().createUserWithEmailAndPassword(userData.email,emailPassword)
            .addOnCompleteListener(activity) {task ->
                if(task.isSuccessful){
                    // in here, we gonna save or write DataCheckOut to into realtime database
                    // but first, we must get user UID for unique user identifier
                    userData.uidUser = initFirebaseAuth().uid.toString()
                    initFirebaseRealtimeUserData().child("UserData").child(userData.uidUser).setValue(userData)
                        .addOnSuccessListener {msg ->
                            Log.d("OnDataUserSaver","Data User Save Successfull : $msg")
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
            }
    }

    fun loginUser(
        email : String,
        password : String,
        activity: Activity,
        onSuccess: () -> Unit = {},
        onFailed: () -> Unit = {}
    ){
        initFirebaseAuth().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity) {task ->
                // in here we get a data user from UID
                // get data for use uid
                val uidUser = initFirebaseAuth().uid
                val uidUserData = initFirebaseRealtimeUserData().child("UserData").child(uidUser.toString())

                uidUserData.get().addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        val getDataUser = task.result.getValue(CreateUserDataClass::class.java)
                        if (getDataUser != null) {
                            Log.d("Uid", getDataUser.uidUser)
                            Log.d("Name", getDataUser.fullname)
                            Log.d("IdNum", getDataUser.idNumber)
                            Log.d("Address", getDataUser.address)
                            Log.d("PhoneNum", getDataUser.phoneNumber)
                            Log.d("Email", getDataUser.email)
                            Log.d("Birthday", getDataUser.birthday)
                            Log.d("Sex", getDataUser.sex)
                        }
                    }
                }

                onSuccess()
            }

            .addOnFailureListener {e ->
                onFailed()
                Log.e("loginUser() Error", "loginUser() Error At : $e")
            }
    }
}
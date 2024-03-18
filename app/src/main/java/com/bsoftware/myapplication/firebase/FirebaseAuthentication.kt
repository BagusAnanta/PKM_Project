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

    fun initFirebaseRealtimeUserData() : DatabaseReference{
        return Firebase.database.reference
    }

    fun createDataUser(
        userData : CreateUserDataClass,
        emailPassword : String,
        onSuccess : () -> Unit = {},
        onFailed : () -> Unit = {},
        activity : Activity
    ){
        initFirebaseAuth().confirmPasswordReset(userData.email,emailPassword)
            .addOnCompleteListener(activity) {task ->
                if(task.isSuccessful){
                    // in here, we gonna save or write DataCheckOut to into realtime database
                    // but first, we must get user UID for unique user identifier
                    userData.uidUser = initFirebaseAuth().uid.toString()
                    initFirebaseRealtimeUserData().child("UserData").setValue(userData)
                        .addOnSuccessListener {
                            Log.d("OnDataUserSaver","Data User Save Successfull")
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
}
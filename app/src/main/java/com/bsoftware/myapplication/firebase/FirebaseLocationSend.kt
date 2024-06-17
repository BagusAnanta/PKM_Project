package com.bsoftware.myapplication.firebase

import android.app.Activity
import android.util.Log
import com.bsoftware.myapplication.dataclass.CreateLocationDataClass
import com.bsoftware.myapplication.sharepref.UidSharePref

class FirebaseLocationSend {

    // get FirebaseAuthUid
    private val firebaseAuthClass = FirebaseAuthentication()

    fun setLocationSend(
        locationLongLatAddr : CreateLocationDataClass,
        onSuccess : () -> Unit = {},
        onError : () -> Unit = {},
        activity : Activity
    ) {
        FirebaseAuthentication().initFirebaseRealtime().child("UserData").child("Location").child(UidSharePref(activity).getUid() ?: "").setValue(locationLongLatAddr)
            .addOnSuccessListener {
                Log.d("FirebaseLocationSend", "sendLocation: success")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("FirebaseLocationSend", "sendLocation: error at : ${it.message}")
                onError()
            }
    }

}
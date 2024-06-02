package com.bsoftware.myapplication.firebase

import android.util.Log
import com.bsoftware.myapplication.dataclass.CreateLocationDataClass

class FirebaseLocationSend {

    // get FirebaseAuthUid
    private val firebaseAuthClass = FirebaseAuthentication()

    fun setLocationSend(
        locationLongLatAddr : CreateLocationDataClass,
        onSuccess : () -> Unit = {},
        onError : () -> Unit = {}
    ) {
        FirebaseAuthentication().initFirebaseRealtime().child(firebaseAuthClass.firstChild).child(firebaseAuthClass.uidUserAuth ?: "").child("Location").setValue(locationLongLatAddr)
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
package com.bsoftware.myapplication.sharepref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class UidSharePref(val activity : Activity) {
    private val uidSharePref : SharedPreferences = activity.getSharedPreferences(UIDSHAREPREF,Context.MODE_PRIVATE)
    private val uidEdit = uidSharePref.edit()

    fun setUid(uid : String){
        uidEdit.apply {
            putString(UIDKEY,uid)
            commit()
        }
    }

    fun getUid() : String?{
        return uidSharePref.getString(UIDKEY,null)
    }

    companion object{
        private const val UIDSHAREPREF = "SharePrefUid"
        private const val UIDKEY = "uid"
    }
}
package com.bsoftware.myapplication.sharepref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class UserLoginSharePref(val activity : Activity) {
    private val loginStatusSharePref : SharedPreferences = activity.getSharedPreferences("LoginStateSharePref", Context.MODE_PRIVATE)
    private val statusEdit = loginStatusSharePref.edit()

    fun setStateLogin(status : Boolean){
        statusEdit.apply {
            putBoolean(LOGIN_STATUS, status)
            commit()
        }
    }

    fun getStatus() : Boolean{
        return loginStatusSharePref.getBoolean(LOGIN_STATUS,false)
    }

    companion object{
        private val LOGIN_STATUS : String = "LoginStatusState"
    }
}
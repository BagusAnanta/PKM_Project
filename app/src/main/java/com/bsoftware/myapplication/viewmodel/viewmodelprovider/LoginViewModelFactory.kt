package com.bsoftware.myapplication.viewmodel.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsoftware.myapplication.sharepref.UserLoginSharePref
import com.bsoftware.myapplication.viewmodel.LoginStateViewModel

class LoginViewModelFactory(private val userLoginSharePref: UserLoginSharePref) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginStateViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginStateViewModel(userLoginSharePref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
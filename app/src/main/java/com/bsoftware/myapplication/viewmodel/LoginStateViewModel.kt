package com.bsoftware.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsoftware.myapplication.sharepref.UserLoginSharePref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginStateViewModel(private val loginState : UserLoginSharePref) : ViewModel(){

    private val _isLogin = MutableStateFlow(false)
    val isLoginIn : StateFlow<Boolean> = _isLogin

    // get login state
    init {
        viewModelScope.launch {
            loginState.isLogginIn.collect{collect ->
                _isLogin.value = collect
            }
        }
    }

    // set login state
    fun setLoginState(isLogin : Boolean){
        viewModelScope.launch {
            loginState.setLoginStatePreference(isLogin)
        }
    }



}
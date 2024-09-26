package com.bsoftware.myapplication.preferencedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.dataclass.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREF_USER_DATA_NAME = "UserDataPreference"

class UserDataDatastore(private val context : Context) {
    companion object{
        private val Context.userDataStore : DataStore<Preferences> by preferencesDataStore(name = PREF_USER_DATA_NAME)
        private val USER_UID_KEY = stringPreferencesKey("user_uid")
        private val USER_FULL_NAME = stringPreferencesKey("user_name")
        private val USER_ID_NUMBER = stringPreferencesKey("user_id_number")
        private val USER_ADDRESS = stringPreferencesKey("user_address")
        private val USER_PHONE_NUMBER = stringPreferencesKey("user_phone_number")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_BIRTHDAY = stringPreferencesKey("user_birthday")
        private val USER_SEX = stringPreferencesKey("user_sex")
    }

    // store a data
    suspend fun storeUserDataProfile(uidUser : String, fullname : String, idNumber : String, address : String, phoneNumber : String, email : String, birthday : String, sex : String){
        context.userDataStore.edit { preference ->
            preference[USER_UID_KEY] = uidUser
            preference[USER_FULL_NAME] = fullname
            preference[USER_ID_NUMBER] = idNumber
            preference[USER_ADDRESS] = address
            preference[USER_PHONE_NUMBER] = phoneNumber
            preference[USER_EMAIL] = email
            preference[USER_BIRTHDAY] = birthday
            preference[USER_SEX] = sex
        }
    }

    // get a data
    val getUserDataProfileFlow : Flow<UserData> = context.userDataStore.data.map {preference ->
        val uidUser = preference[USER_UID_KEY] ?: ""
        val fullName = preference[USER_FULL_NAME] ?: ""
        val idUser = preference[USER_ID_NUMBER] ?: ""
        val addressUser = preference[USER_ADDRESS] ?: ""
        val phoneNumUser = preference[USER_PHONE_NUMBER] ?: ""
        val emailUser = preference[USER_EMAIL] ?: ""
        val birthdayUser = preference[USER_BIRTHDAY] ?: ""
        val sexUser = preference[USER_SEX] ?: ""

        UserData(uidUser,fullName,idUser,addressUser,phoneNumUser,emailUser,birthdayUser,sexUser)
    }

    val getUidUser : Flow<String> = context.userDataStore.data.map {preference ->
        preference[USER_UID_KEY] ?: ""
    }

}
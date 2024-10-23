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
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PHONE_NUMBER = stringPreferencesKey("user_phone_number")
        private val USER_ADDRESS = stringPreferencesKey("user_address")
        private val USER_VERIFIED_AT = stringPreferencesKey("user_verified_at")
        private val USER_TOKEN = stringPreferencesKey("user_token")
        private val USER_IS_ADMIN = stringPreferencesKey("user_is_admin")
        private val USER_REMEMBER_TOKEN = stringPreferencesKey("user_remember_token")
        private val USER_CREATED_AT = stringPreferencesKey("user_created_at")
        private val USER_UPDATED_AT = stringPreferencesKey("user_updated_at")
    }

    // store a data
    suspend fun storeUserDataProfile(
        id : String,
        name : String,
        email : String,
        phoneNumber : String,
        address : String,
        verifiedAt : String,
        token : String,
        isAdmin : String,
        rememberToken : String,
        createdAt : String,
        updatedAt : String
    ){
        context.userDataStore.edit { preference ->
            preference[USER_ID_KEY] = id
            preference[USER_NAME] = name
            preference[USER_EMAIL] = email
            preference[USER_PHONE_NUMBER] = phoneNumber
            preference[USER_ADDRESS] = address
            preference[USER_VERIFIED_AT] = verifiedAt
            preference[USER_TOKEN] = token
            preference[USER_IS_ADMIN] = isAdmin
            preference[USER_REMEMBER_TOKEN] = rememberToken
            preference[USER_CREATED_AT] = createdAt
            preference[USER_UPDATED_AT] = updatedAt
        }
    }

    // get a data
    val getUserDataProfileFlow : Flow<UserData> = context.userDataStore.data.map {preference ->
        val id = preference[USER_ID_KEY] ?: ""
        val name = preference[USER_NAME] ?: ""
        val email = preference[USER_EMAIL] ?: ""
        val phoneNumber = preference[USER_PHONE_NUMBER] ?: ""
        val address = preference[USER_ADDRESS] ?: ""
        val verifiedAt = preference[USER_VERIFIED_AT] ?: ""
        val token = preference[USER_TOKEN] ?: ""
        val isAdmin = preference[USER_IS_ADMIN] ?: ""
        val rememberToken = preference[USER_REMEMBER_TOKEN] ?: ""
        val createdAt = preference[USER_CREATED_AT] ?: ""
        val updatedAt = preference[USER_UPDATED_AT] ?: ""

        UserData(
            id = id,
            name = name,
            email = email,
            nohp = phoneNumber,
            alamat = address,
            verified_at = verifiedAt,
            token = token,
            is_admin = isAdmin,
            remember_token = rememberToken,
            created_at = createdAt,
            updated_at = updatedAt
        )
    }

    val getUidUser : Flow<String> = context.userDataStore.data.map {preference ->
        preference[USER_ID_KEY] ?: ""
    }

}
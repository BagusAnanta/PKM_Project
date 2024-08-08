package com.bsoftware.myapplication.sharepref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserLoginSharePref(val activity : Activity) {
    private val loginStatusSharePref : SharedPreferences = activity.getSharedPreferences(LOGINSTATESHAREPREF, Context.MODE_PRIVATE)
    private val statusEdit = loginStatusSharePref.edit()

    // Datastore Preference Instance
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = LOGINSTATESHAREPREF,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context = context, sharedPreferencesName = LOGINSTATESHAREPREF))
        }
    )

    // Read and Write Preference
    fun getLoginStatePreference(context : Context) : Flow<Boolean> {
        return context.dataStore.data.catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            preference[LOGINSTATUSKEY] ?: false
        }
    }

    suspend fun setLoginStatePreference(context : Context, status : Boolean){
        context.dataStore.edit { preference ->
            preference[LOGINSTATUSKEY] = status
        }
    }

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
        private val LOGINSTATESHAREPREF = "LoginStateSharePref"
        private val LOGIN_STATUS : String = "LoginStatusState"

        // Datastore preference key
        private val LOGINSTATUSKEY = booleanPreferencesKey(LOGIN_STATUS)
    }
}
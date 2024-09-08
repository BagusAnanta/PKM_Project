package com.bsoftware.myapplication.sharepref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = UserLoginSharePref.LoginStateStatusManager.LOGINSTATESTATUS,
)

class UserLoginSharePref(var context : Context) {

    private val dataStore = context.dataStore

    object LoginStateStatusManager{

        // private const val LOGINSTATESHAREPREF = "LoginStateSharePref"
        const val LOGINSTATESTATUS = "LoginStateStatusDatStorePref.preferences_pb"
        private const val LOGIN_STATUS: String = "LoginStatusState"

        // Datastore preference key
        val LOGINSTATUSKEY = booleanPreferencesKey(LOGIN_STATUS)
    }

    // Read and Write Preference
   val isLogginIn : Flow<Boolean> = context.dataStore.data.map { preference ->
       preference[LoginStateStatusManager.LOGINSTATUSKEY] ?: false
    }

    suspend fun setLoginStatePreference(status: Boolean) {
        dataStore.edit { preference ->
            preference[LoginStateStatusManager.LOGINSTATUSKEY] = status
        }
    }
}

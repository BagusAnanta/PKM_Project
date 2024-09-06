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
import com.bsoftware.myapplication.sharepref.UserLoginSharePref.LoginStateStatusManager.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserLoginSharePref() {

    object LoginStateStatusManager{

        // private const val LOGINSTATESHAREPREF = "LoginStateSharePref"
        private const val LOGINSTATESTATUS = "LoginStateStatusDatStorePref.preferences_pb"
        private const val LOGIN_STATUS: String = "LoginStatusState"

        // Datastore preference key
        val LOGINSTATUSKEY = booleanPreferencesKey(LOGIN_STATUS)

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = LOGINSTATESTATUS,
        )
    }

    // Read and Write Preference
    suspend fun getLoginStatePreference(context: Context): Boolean {
        val dataStorage = context.dataStore.data.first()
        return dataStorage[LoginStateStatusManager.LOGINSTATUSKEY] ?: false
    }

    suspend fun setLoginStatePreference(context: Context, status: Boolean) {
        context.dataStore.edit { preference ->
            preference[LoginStateStatusManager.LOGINSTATUSKEY] = status
        }
    }
}

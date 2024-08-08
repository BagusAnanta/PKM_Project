package com.bsoftware.myapplication.sharepref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UidSharePref(val activity : Activity) {
    private val uidSharePref : SharedPreferences = activity.getSharedPreferences(UIDSHAREPREF,Context.MODE_PRIVATE)
    private val uidEdit = uidSharePref.edit()

    // DataStore Preference Instance
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = UIDSHAREPREF,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context = context, sharedPreferencesName = UIDSHAREPREF))
        }
    )

    // Read Preference
    fun getUidPreference(context : Context) : Flow<String>{
        return context.dataStore.data.catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            preference[UIDKEYDATASTORE] ?: ""
        }
    }

    suspend fun setUidPreference(context : Context,uid : String){
        context.dataStore.edit { preference ->
            preference[UIDKEYDATASTORE] = uid
        }
    }

    // sharePreference segment below
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

        // datastore preference key
        private val UIDKEYDATASTORE = stringPreferencesKey(UIDKEY)
    }
}
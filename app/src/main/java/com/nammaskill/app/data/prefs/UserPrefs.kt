package com.nammaskill.app.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

data class UserProfile(
    val name: String,
    val phone: String,
    val village: String,
    val favoriteTrades: Set<String>,
)

class UserPrefs(private val context: Context) {

    private object Keys {
        val NAME = stringPreferencesKey("name")
        val PHONE = stringPreferencesKey("phone")
        val VILLAGE = stringPreferencesKey("village")
        val FAV_TRADES = stringSetPreferencesKey("fav_trades")
    }

    val profileFlow: Flow<UserProfile> = context.dataStore.data.map { prefs: Preferences ->
        UserProfile(
            name = prefs[Keys.NAME] ?: "",
            phone = prefs[Keys.PHONE] ?: "",
            village = prefs[Keys.VILLAGE] ?: "",
            favoriteTrades = prefs[Keys.FAV_TRADES] ?: emptySet(),
        )
    }

    suspend fun saveProfile(name: String, phone: String, village: String, favoriteTrades: Set<String>) {
        context.dataStore.edit { prefs ->
            prefs[Keys.NAME] = name
            prefs[Keys.PHONE] = phone
            prefs[Keys.VILLAGE] = village
            prefs[Keys.FAV_TRADES] = favoriteTrades
        }
    }
}


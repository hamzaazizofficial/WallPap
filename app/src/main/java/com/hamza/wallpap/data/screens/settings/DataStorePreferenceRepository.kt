package com.hamza.wallpap.data.screens.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferenceRepository(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("themeKey")
        val COUNT_KEY = intPreferencesKey("theme_key")
    }

    val getThemeValue: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[COUNT_KEY] ?: 0
        }

    suspend fun saveThemeValue(themeValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[COUNT_KEY] = themeValue
        }
    }
}
package com.example.repositoryg_group3

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create the DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataStoreManager {

    private val TEXT_KEY = stringPreferencesKey("saved_text")
    private val THEME_KEY = stringPreferencesKey("saved_theme") // Key for the theme

    // Function to save text
    suspend fun saveText(context: Context, text: String) {
        context.dataStore.edit { settings ->
            settings[TEXT_KEY] = text
        }
    }

    // Function to get text
    fun getText(context: Context): Flow<String?> {
        return context.dataStore.data.map { settings ->
            settings[TEXT_KEY]
        }
    }

    // Function to save the selected theme
    suspend fun saveTheme(context: Context, theme: String) {
        context.dataStore.edit { settings ->
            settings[THEME_KEY] = theme
        }
    }

    // **FIX: Add the missing getTheme function**
    fun getTheme(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            // Return the saved theme, or "Default" if none is saved.
            preferences[THEME_KEY] ?: "Default"
        }
    }
}

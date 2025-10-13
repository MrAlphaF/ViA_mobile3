package com.example.repositoryg_group3

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Define dataStore as a top-level extension property with a single name
val Context.dataStore by preferencesDataStore(name = "userPreferences")

object DataStoreManager {
    // Define a constant for the saved text key
    private val DATA_STORE_KEY = stringPreferencesKey("saved_text")

    // Save text to DataStore
    suspend fun saveText(context: Context, text: String) {
        context.dataStore.edit { preferences ->
            preferences[DATA_STORE_KEY] = text
        }
    }

    // Read text from DataStore
    suspend fun getText(context: Context): String? {
        val preferences = context.dataStore.data.first()
        return preferences[DATA_STORE_KEY]
    }

    // Clear specific key (optional, for future use)
    suspend fun clearText(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(DATA_STORE_KEY)
        }
    }
}
package com.example.datastoreempty.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastoreempty.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Repository that stores the user's preferences for the ui theme
 */
class SettingsRepository(private val context: Context) {

    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "users_preferences")

        // la chiave non è una banale stringa ma è una preferences.key, che prende una string in input
        private val UI_THEME = stringPreferencesKey(name = "ui_theme")
    }

    // Nel datastore i dati sono salvati come un flow
    val preferenceFlow: Flow<String> = context.datastore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { pref ->
        // restituiamo quella che è nella nostra ui_theme ma di default l'app parte con la light mode
        pref[UI_THEME] ?: context.getString(R.string.light_theme)
    }

    suspend fun saveToDataStore(theme: String) {
        context.datastore.edit { pref ->
            pref[UI_THEME] = theme
        }
    }
}


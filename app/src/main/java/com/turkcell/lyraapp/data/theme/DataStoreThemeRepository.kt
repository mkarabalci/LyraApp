package com.turkcell.lyraapp.data.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

class DataStoreThemeRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : ThemeRepository {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val isDarkKey = booleanPreferencesKey("is_dark")

    override val isDark: StateFlow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[isDarkKey] ?: false }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    override suspend fun toggle() {
        context.dataStore.edit { prefs ->
            prefs[isDarkKey] = !(prefs[isDarkKey] ?: false)
        }
    }
}

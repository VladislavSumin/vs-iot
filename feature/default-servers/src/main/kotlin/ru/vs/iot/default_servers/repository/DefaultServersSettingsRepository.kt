package ru.vs.iot.default_servers.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val IS_DEFAULT_SERVERS_INITIALIZED = booleanPreferencesKey("is_default_servers_initialized")

internal interface DefaultServersSettingsRepository {
    suspend fun isDefaultServersInitialized(): Boolean
    suspend fun setDefaultServersInitialized()
}

internal class DefaultServersSettingsRepositoryImpl(
    context: Context
) : DefaultServersSettingsRepository {
    private val Context.dataStore by preferencesDataStore(name = "default_servers_settings")
    private val dataStore = context.dataStore

    override suspend fun isDefaultServersInitialized(): Boolean {
        return dataStore.data.first()[IS_DEFAULT_SERVERS_INITIALIZED] ?: false
    }

    override suspend fun setDefaultServersInitialized() {
        dataStore.edit {
            it[IS_DEFAULT_SERVERS_INITIALIZED] = true
        }
    }
}

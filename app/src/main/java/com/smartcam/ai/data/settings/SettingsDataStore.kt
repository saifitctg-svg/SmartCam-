package com.smartcam.ai.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.smartCamDataStore by preferencesDataStore(name = "smartcam_settings")

data class AppSettings(
    val darkTheme: Boolean = false,
    val monitoringNoticeEnabled: Boolean = true,
    val retentionDays: Int = 7
)

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val settings: Flow<AppSettings> = context.smartCamDataStore.data.map { preferences ->
        AppSettings(
            darkTheme = preferences[DARK_THEME] ?: false,
            monitoringNoticeEnabled = preferences[MONITORING_NOTICE] ?: true,
            retentionDays = preferences[RETENTION_DAYS] ?: 7
        )
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.smartCamDataStore.edit { it[DARK_THEME] = enabled }
    }

    suspend fun setMonitoringNoticeEnabled(enabled: Boolean) {
        context.smartCamDataStore.edit { it[MONITORING_NOTICE] = enabled }
    }

    suspend fun setRetentionDays(days: Int) {
        context.smartCamDataStore.edit { it[RETENTION_DAYS] = days.coerceAtLeast(1) }
    }

    private companion object {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val MONITORING_NOTICE = booleanPreferencesKey("monitoring_notice")
        val RETENTION_DAYS = intPreferencesKey("retention_days")
    }
}

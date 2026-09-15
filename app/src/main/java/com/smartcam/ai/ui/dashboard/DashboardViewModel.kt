package com.smartcam.ai.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartcam.ai.core.AppLogger
import com.smartcam.ai.data.local.SecurityEventEntity
import com.smartcam.ai.data.repository.SmartCamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val isLoading: Boolean = true,
    val camerasOnline: Int = 0,
    val camerasOffline: Int = 0,
    val todaysEvents: Int = 0,
    val criticalAlerts: Int = 0,
    val peopleDetected: Int = 0,
    val activityMinutes: Int = 0,
    val recentEvents: List<SecurityEventEntity> = emptyList(),
    val isDemoData: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    repository: SmartCamRepository,
    logger: AppLogger
) : ViewModel() {
    val uiState: StateFlow<DashboardUiState> = repository.observeDashboard()
        .map { snapshot ->
            DashboardUiState(
                isLoading = false,
                camerasOnline = snapshot.camerasOnline,
                camerasOffline = snapshot.camerasOffline,
                todaysEvents = snapshot.todaysEvents,
                criticalAlerts = snapshot.criticalAlerts,
                peopleDetected = snapshot.peopleDetected,
                activityMinutes = snapshot.activityMinutes,
                recentEvents = snapshot.recentEvents,
                isDemoData = snapshot.isDemoData
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())

    init {
        logger.debug("DashboardViewModel started with demo repository")
    }
}

package com.smartcam.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartcam.ai.ui.dashboard.DashboardScreen
import com.smartcam.ai.ui.navigation.Screen
import com.smartcam.ai.ui.app.ActivityScreen
import com.smartcam.ai.ui.app.AddCameraScreen
import com.smartcam.ai.ui.app.CameraScreen
import com.smartcam.ai.ui.app.EventsScreen
import com.smartcam.ai.ui.app.FeatureStatusScreen
import com.smartcam.ai.ui.app.PrivacyScreen
import com.smartcam.ai.ui.dashboard.DashboardViewModel
import com.smartcam.ai.data.settings.AppSettings
import com.smartcam.ai.data.settings.SettingsDataStore
import com.smartcam.ai.ui.theme.SmartCamTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settings = settingsDataStore.settings.collectAsStateWithLifecycle(initialValue = AppSettings()).value
            SmartCamTheme(useDarkTheme = settings.darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Dashboard.route
                    ) {
                        composable(Screen.Dashboard.route) {
                            val viewModel: DashboardViewModel = hiltViewModel()
                            DashboardScreen(
                                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                                onNavigateToCameras = { navController.navigate(Screen.Cameras.route) },
                                onNavigateToEvents = { navController.navigate(Screen.Events.route) },
                                onNavigateToActivity = { navController.navigate(Screen.Activity.route) },
                                onNavigateToReports = { navController.navigate(Screen.Reports.route) },
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                                onNavigateToPrivacy = { navController.navigate(Screen.Privacy.route) }
                            )
                        }
                        composable(Screen.Cameras.route) {
                            CameraScreen(
                                onBack = { navController.popBackStack() },
                                onAddCamera = { navController.navigate(Screen.AddCamera.route) }
                            )
                        }
                        composable(Screen.AddCamera.route) { AddCameraScreen { navController.popBackStack() } }
                        composable(Screen.Events.route) { EventsScreen { navController.popBackStack() } }
                        composable(Screen.Activity.route) { ActivityScreen { navController.popBackStack() } }
                        composable(Screen.Reports.route) { FeatureStatusScreen("Reports") { navController.popBackStack() } }
                        composable(Screen.Notifications.route) { FeatureStatusScreen("Notifications") { navController.popBackStack() } }
                        composable(Screen.Storage.route) { FeatureStatusScreen("Storage") { navController.popBackStack() } }
                        composable(Screen.Users.route) { FeatureStatusScreen("Users & Permissions") { navController.popBackStack() } }
                        composable(Screen.Audit.route) { FeatureStatusScreen("Audit Logs") { navController.popBackStack() } }
                        composable(Screen.Settings.route) { FeatureStatusScreen("Settings") { navController.popBackStack() } }
                        composable(Screen.Privacy.route) { PrivacyScreen { navController.popBackStack() } }
                        composable(Screen.LiveView.route) { FeatureStatusScreen("Live Camera") { navController.popBackStack() } }
                    }
                }
            }
        }
    }
}

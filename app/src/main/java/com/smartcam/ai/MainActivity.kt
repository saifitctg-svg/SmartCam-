package com.smartcam.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
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
                            DashboardScreen(
                                onNavigateToCameras = { navController.navigate(Screen.Cameras.route) },
                                onNavigateToEvents = { navController.navigate(Screen.Events.route) },
                                onNavigateToActivity = { navController.navigate(Screen.Activity.route) },
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

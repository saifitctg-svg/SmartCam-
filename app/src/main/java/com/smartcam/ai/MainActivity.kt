package com.smartcam.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartcam.ai.ui.dashboard.DashboardScreen
import com.smartcam.ai.ui.navigation.Screen
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
                        composable(Screen.Cameras.route) { PlaceholderScreen("Cameras") }
                        composable(Screen.AddCamera.route) { PlaceholderScreen("Add Camera") }
                        composable(Screen.Events.route) { PlaceholderScreen("Events") }
                        composable(Screen.Activity.route) { PlaceholderScreen("Activity") }
                        composable(Screen.Settings.route) { PlaceholderScreen("Settings") }
                        composable(Screen.Privacy.route) { PlaceholderScreen("Privacy & Consent") }
                        composable(Screen.LiveView.route) { PlaceholderScreen("Live View") }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(24.dp)
        )
    }
}
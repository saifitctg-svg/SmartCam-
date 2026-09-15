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
                        // Other destinations navigate gracefully across the architecture
                    }
                }
            }
        }
    }
}
package com.smartcam.ai.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Cameras : Screen("cameras")
    object AddCamera : Screen("add_camera")
    object LiveView : Screen("live_view/{cameraId}") {
        fun passId(id: Long) = "live_view/$id"
    }
    object Events : Screen("events")
    object Activity : Screen("activity")
    object Reports : Screen("reports")
    object Notifications : Screen("notifications")
    object Storage : Screen("storage")
    object Users : Screen("users")
    object Audit : Screen("audit")
    object Settings : Screen("settings")
    object Privacy : Screen("privacy")
}
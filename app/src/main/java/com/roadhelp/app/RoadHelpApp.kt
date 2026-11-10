package com.roadhelp.app

import androidx.compose.runtime.Composable
import com.roadhelp.app.core.design.RoadHelpTheme
import com.roadhelp.app.feature.home.HomeScreen

/**
 * Application-level Compose root.
 *
 * Global concerns such as theme, navigation and app-wide status UI belong here,
 * not inside an individual feature screen or Activity.
 */
@Composable
fun RoadHelpApp(isOffline: Boolean) {
    RoadHelpTheme {
        HomeScreen(isOffline = isOffline)
    }
}

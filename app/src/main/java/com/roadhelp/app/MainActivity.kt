package com.roadhelp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.roadhelp.app.core.design.RoadHelpTheme
import com.roadhelp.app.core.network.ConnectivityMonitor
import com.roadhelp.app.presentation.home.HomeScreen

class MainActivity : ComponentActivity() {

    private lateinit var connectivityMonitor: ConnectivityMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityMonitor = ConnectivityMonitor(applicationContext)

        setContent {
            val isOnline by connectivityMonitor.isOnline.collectAsState()

            RoadHelpTheme {
                HomeScreen(isOffline = !isOnline)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityMonitor.start()
    }

    override fun onStop() {
        connectivityMonitor.stop()
        super.onStop()
    }
}

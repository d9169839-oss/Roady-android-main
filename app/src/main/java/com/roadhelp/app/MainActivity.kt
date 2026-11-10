package com.roadhelp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.roadhelp.app.core.network.ConnectivityMonitor
import com.roadhelp.app.data.location.FusedDeviceLocationRepository
import com.roadhelp.app.feature.home.HomeViewModel

/**
 * Android entry point only.
 *
 * Creates platform adapters and their screen ViewModel, then bridges lifecycle
 * and observable state into the Compose application root.
 */
class MainActivity : ComponentActivity() {

    private lateinit var connectivityMonitor: ConnectivityMonitor
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityMonitor = ConnectivityMonitor(applicationContext)
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModel.Factory(FusedDeviceLocationRepository(applicationContext))
        )[HomeViewModel::class.java]

        setContent {
            val isOnline by connectivityMonitor.isOnline.collectAsState()
            RoadHelpApp(
                isOffline = !isOnline,
                homeViewModel = homeViewModel
            )
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

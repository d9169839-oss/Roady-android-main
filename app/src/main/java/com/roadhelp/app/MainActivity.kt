package com.roadhelp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.roadhelp.app.core.network.ConnectivityMonitor

/**
 * Android entry point only.
 *
 * Platform lifecycle and connectivity are bridged into the Compose application root;
 * feature UI and screen state are kept out of this Activity.
 */
class MainActivity : ComponentActivity() {

    private lateinit var connectivityMonitor: ConnectivityMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityMonitor = ConnectivityMonitor(applicationContext)

        setContent {
            val isOnline by connectivityMonitor.isOnline.collectAsState()
            RoadHelpApp(isOffline = !isOnline)
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

package com.roadhelp.app.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Observes whether Android's current default network has validated internet access.
 *
 * This is event-driven: Android invokes the callback when network state changes.
 * The monitor does not poll, start a background service, or retain an Activity.
 */
class ConnectivityMonitor(context: Context) {

    private val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isOnline = MutableStateFlow(hasValidatedInternet())
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private var isRegistered = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            refreshDefaultNetworkState()
        }

        override fun onLost(network: Network) {
            refreshDefaultNetworkState()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            // This callback belongs to the default network registered below.
            _isOnline.value = networkCapabilities.hasValidatedInternet()
        }
    }

    /** Start observing while the Activity is in the foreground. Safe to call repeatedly. */
    @Synchronized
    fun start() {
        if (isRegistered) return

        refreshDefaultNetworkState()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        isRegistered = true
    }

    /** Stop observing when the Activity leaves the foreground. */
    @Synchronized
    fun stop() {
        if (!isRegistered) return

        connectivityManager.unregisterNetworkCallback(networkCallback)
        isRegistered = false
    }

    private fun refreshDefaultNetworkState() {
        _isOnline.value = hasValidatedInternet()
    }

    private fun hasValidatedInternet(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasValidatedInternet()
    }

    private fun NetworkCapabilities.hasValidatedInternet(): Boolean =
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

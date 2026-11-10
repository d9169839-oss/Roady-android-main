package com.roadhelp.app.data.location

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.roadhelp.app.domain.model.GeoPoint
import com.roadhelp.app.domain.repository.DeviceLocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Google Play Services adapter for a single balanced-power location request.
 *
 * It does not subscribe to continuous updates or persist coordinates. The caller
 * must obtain foreground permission before invoking this repository.
 */
class FusedDeviceLocationRepository(context: Context) : DeviceLocationRepository {

    private val client = LocationServices.getFusedLocationProviderClient(context.applicationContext)

    override suspend fun getCurrentLocation(): GeoPoint? =
        suspendCancellableCoroutine { continuation ->
            val cancellation = CancellationTokenSource()

            client.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                cancellation.token
            ).addOnSuccessListener { location ->
                if (continuation.isActive) {
                    continuation.resume(
                        location?.let { GeoPoint(it.latitude, it.longitude) }
                    )
                }
            }.addOnFailureListener { error ->
                if (continuation.isActive) continuation.resumeWithException(error)
            }

            continuation.invokeOnCancellation { cancellation.cancel() }
        }
}

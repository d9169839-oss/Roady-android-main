package com.roadhelp.app.domain.repository

import com.roadhelp.app.domain.model.GeoPoint

/**
 * Contract for obtaining one foreground location fix.
 * The domain contract does not expose Android Location or Google Play Services types.
 */
interface DeviceLocationRepository {
    suspend fun getCurrentLocation(): GeoPoint?
}

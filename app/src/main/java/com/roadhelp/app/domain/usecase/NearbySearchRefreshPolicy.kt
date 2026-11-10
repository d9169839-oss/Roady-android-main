package com.roadhelp.app.domain.usecase

import com.roadhelp.app.domain.model.GeoPoint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Decides whether an existing nearby-provider result set is stale enough to refresh.
 *
 * Both conditions must be true:
 * 1. The user has moved at least [minimumMovementMeters] from the origin of the
 *    last successful search.
 * 2. The last successful results are at least [minimumResultAgeMillis] old.
 *
 * This policy does not schedule timers or request location updates. The caller
 * supplies timestamps and coordinates obtained from the platform/data layers.
 */
class NearbySearchRefreshPolicy(
    private val minimumMovementMeters: Double = DEFAULT_MINIMUM_MOVEMENT_METERS,
    private val minimumResultAgeMillis: Long = DEFAULT_MINIMUM_RESULT_AGE_MILLIS
) {
    init {
        require(minimumMovementMeters >= 0.0) { "Movement threshold cannot be negative." }
        require(minimumResultAgeMillis >= 0L) { "Result age threshold cannot be negative." }
    }

    fun shouldRefresh(
        currentLocation: GeoPoint,
        lastSuccessfulSearchLocation: GeoPoint,
        lastSuccessfulSearchAtEpochMillis: Long,
        nowEpochMillis: Long
    ): Boolean {
        // A clock correction must not make results appear older than they are.
        if (nowEpochMillis < lastSuccessfulSearchAtEpochMillis) return false

        val movedMeters = distanceMeters(currentLocation, lastSuccessfulSearchLocation)
        val resultAgeMillis = nowEpochMillis - lastSuccessfulSearchAtEpochMillis

        return movedMeters >= minimumMovementMeters &&
            resultAgeMillis >= minimumResultAgeMillis
    }

    /** Haversine distance on a mean-earth-radius sphere, returned in metres. */
    internal fun distanceMeters(start: GeoPoint, end: GeoPoint): Double {
        val earthRadiusMeters = 6_371_000.0
        val startLatitude = Math.toRadians(start.latitude)
        val endLatitude = Math.toRadians(end.latitude)
        val latitudeDelta = Math.toRadians(end.latitude - start.latitude)
        val longitudeDelta = Math.toRadians(end.longitude - start.longitude)

        val haversine = sin(latitudeDelta / 2) * sin(latitudeDelta / 2) +
            cos(startLatitude) * cos(endLatitude) *
            sin(longitudeDelta / 2) * sin(longitudeDelta / 2)

        val centralAngle = 2 * atan2(sqrt(haversine), sqrt(1 - haversine))
        return earthRadiusMeters * centralAngle
    }

    private companion object {
        const val DEFAULT_MINIMUM_MOVEMENT_METERS = 300.0
        const val DEFAULT_MINIMUM_RESULT_AGE_MILLIS = 5 * 60 * 1_000L
    }
}

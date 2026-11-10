package com.roadhelp.app.domain.usecase

import com.roadhelp.app.domain.model.GeoPoint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Refreshes only when the user has moved far enough AND the last successful
 * result set is old enough. This is a pure policy: it does not schedule updates.
 */
class NearbySearchRefreshPolicy(
    private val minimumMovementMeters: Double = DEFAULT_MINIMUM_MOVEMENT_METERS,
    private val minimumResultAgeMillis: Long = DEFAULT_MINIMUM_RESULT_AGE_MILLIS
) {
    init {
        require(minimumMovementMeters >= 0.0)
        require(minimumResultAgeMillis >= 0L)
    }

    fun shouldRefresh(
        currentLocation: GeoPoint,
        lastSuccessfulSearchLocation: GeoPoint,
        lastSuccessfulSearchAtEpochMillis: Long,
        nowEpochMillis: Long
    ): Boolean {
        if (nowEpochMillis < lastSuccessfulSearchAtEpochMillis) return false

        val movedMeters = distanceMeters(currentLocation, lastSuccessfulSearchLocation)
        val resultAgeMillis = nowEpochMillis - lastSuccessfulSearchAtEpochMillis

        return movedMeters >= minimumMovementMeters &&
            resultAgeMillis >= minimumResultAgeMillis
    }

    internal fun distanceMeters(start: GeoPoint, end: GeoPoint): Double {
        val earthRadiusMeters = 6_371_000.0
        val startLatitude = Math.toRadians(start.latitude)
        val endLatitude = Math.toRadians(end.latitude)
        val latitudeDelta = Math.toRadians(end.latitude - start.latitude)
        val longitudeDelta = Math.toRadians(end.longitude - start.longitude)

        val haversine = sin(latitudeDelta / 2) * sin(latitudeDelta / 2) +
            cos(startLatitude) * cos(endLatitude) *
            sin(longitudeDelta / 2) * sin(longitudeDelta / 2)

        return earthRadiusMeters * 2 * atan2(sqrt(haversine), sqrt(1 - haversine))
    }

    private companion object {
        const val DEFAULT_MINIMUM_MOVEMENT_METERS = 300.0
        const val DEFAULT_MINIMUM_RESULT_AGE_MILLIS = 5 * 60 * 1_000L
    }
}

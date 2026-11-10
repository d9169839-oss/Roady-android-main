package com.roadhelp.app.domain.model

/**
 * A latitude/longitude pair used by domain location and distance policies.
 *
 * This type deliberately has no Android Location dependency so it remains
 * straightforward to unit test and can be reused by different location sources.
 */
data class GeoPoint(
    val latitude: Double,
    val longitude: Double
) {
    init {
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90 degrees." }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180 degrees." }
    }
}

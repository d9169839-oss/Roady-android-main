package com.roadhelp.app

import com.roadhelp.app.domain.model.GeoPoint
import com.roadhelp.app.domain.usecase.NearbySearchRefreshPolicy
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NearbySearchRefreshPolicyTest {
    private val policy = NearbySearchRefreshPolicy()
    private val origin = GeoPoint(latitude = 0.0, longitude = 0.0)
    private val fiveMinutes = 5 * 60 * 1_000L

    @Test
    fun refreshesWhenMovementAndFreshnessThresholdsAreMet() {
        val movedBeyond300Meters = GeoPoint(latitude = 0.0, longitude = 0.00270)
        assertTrue(policy.shouldRefresh(movedBeyond300Meters, origin, 1_000L, 1_000L + fiveMinutes))
    }

    @Test
    fun doesNotRefreshWhenMovementThresholdIsNotMet() {
        val movedLessThan300Meters = GeoPoint(latitude = 0.0, longitude = 0.001)
        assertFalse(policy.shouldRefresh(movedLessThan300Meters, origin, 1_000L, 1_000L + fiveMinutes))
    }

    @Test
    fun doesNotRefreshWhenResultsAreStillFresh() {
        val movedBeyond300Meters = GeoPoint(latitude = 0.0, longitude = 0.004)
        assertFalse(policy.shouldRefresh(movedBeyond300Meters, origin, 1_000L, 1_000L + fiveMinutes - 1L))
    }

    @Test
    fun doesNotRefreshWhenNeitherConditionIsMet() {
        val nearbyPoint = GeoPoint(latitude = 0.0, longitude = 0.001)
        assertFalse(policy.shouldRefresh(nearbyPoint, origin, 1_000L, 1_000L + 60_000L))
    }

    @Test
    fun doesNotRefreshWhenClockMovesBackwards() {
        val movedBeyond300Meters = GeoPoint(latitude = 0.0, longitude = 0.004)
        assertFalse(policy.shouldRefresh(movedBeyond300Meters, origin, 10_000L, 9_999L))
    }
}

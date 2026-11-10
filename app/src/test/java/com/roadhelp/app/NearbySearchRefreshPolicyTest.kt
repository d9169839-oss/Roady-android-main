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
    fun refreshesWhenBothMovementAndFreshnessThresholdsAreMet() {
        val movedJustOver300Meters = GeoPoint(latitude = 0.0, longitude = 0.00270)

        assertTrue(
            policy.shouldRefresh(
                currentLocation = movedJustOver300Meters,
                lastSuccessfulSearchLocation = origin,
                lastSuccessfulSearchAtEpochMillis = 1_000L,
                nowEpochMillis = 1_000L + fiveMinutes
            )
        )
    }

    @Test
    fun doesNotRefreshWhenUserHasNotMovedEnoughEvenIfResultsAreOld() {
        val movedLessThan300Meters = GeoPoint(latitude = 0.0, longitude = 0.001)

        assertFalse(
            policy.shouldRefresh(
                currentLocation = movedLessThan300Meters,
                lastSuccessfulSearchLocation = origin,
                lastSuccessfulSearchAtEpochMillis = 1_000L,
                nowEpochMillis = 1_000L + fiveMinutes
            )
        )
    }

    @Test
    fun doesNotRefreshWhenResultsAreFreshEvenIfUserMovedEnough() {
        val movedBeyond300Meters = GeoPoint(latitude = 0.0, longitude = 0.004)

        assertFalse(
            policy.shouldRefresh(
                currentLocation = movedBeyond300Meters,
                lastSuccessfulSearchLocation = origin,
                lastSuccessfulSearchAtEpochMillis = 1_000L,
                nowEpochMillis = 1_000L + fiveMinutes - 1L
            )
        )
    }

    @Test
    fun doesNotRefreshWhenNeitherConditionIsMet() {
        assertFalse(
            policy.shouldRefresh(
                currentLocation = GeoPoint(latitude = 0.0, longitude = 0.001),
                lastSuccessfulSearchLocation = origin,
                lastSuccessfulSearchAtEpochMillis = 1_000L,
                nowEpochMillis = 1_000L + 60_000L
            )
        )
    }

    @Test
    fun doesNotRefreshWhenClockIsEarlierThanLastSuccessfulSearch() {
        assertFalse(
            policy.shouldRefresh(
                currentLocation = GeoPoint(latitude = 0.0, longitude = 0.004),
                lastSuccessfulSearchLocation = origin,
                lastSuccessfulSearchAtEpochMillis = 10_000L,
                nowEpochMillis = 9_999L
            )
        )
    }
}

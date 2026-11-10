package com.roadhelp.app

import com.roadhelp.app.domain.model.GeoPoint
import org.junit.Assert.assertEquals
import org.junit.Test

class GeoPointTest {
    @Test
    fun acceptsValidCoordinates() {
        val point = GeoPoint(latitude = 33.6844, longitude = 73.0479)
        assertEquals(33.6844, point.latitude, 0.0)
        assertEquals(73.0479, point.longitude, 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsLatitudeOutsideValidRange() {
        GeoPoint(latitude = 91.0, longitude = 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsLongitudeOutsideValidRange() {
        GeoPoint(latitude = 0.0, longitude = 181.0)
    }
}

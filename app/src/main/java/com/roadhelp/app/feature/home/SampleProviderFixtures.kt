package com.roadhelp.app.feature.home

import com.roadhelp.app.domain.model.ProviderCategory
import com.roadhelp.app.domain.model.RoadsideProvider

/**
 * Explicit preview fixtures only. Replace this source with repository data
 * when the real provider-discovery API is connected.
 */
internal fun sampleProviders(): List<RoadsideProvider> = listOf(
    RoadsideProvider("sample-1", "City Tow Assistance", ProviderCategory.TOW, 1_200, "Sample location", null, 4.7, 83, null, previewOnly = true),
    RoadsideProvider("sample-2", "Rapid Tyre Support", ProviderCategory.TYRE, 2_600, "Sample location", null, 4.5, 41, null, previewOnly = true),
    RoadsideProvider("sample-3", "Battery Rescue", ProviderCategory.BATTERY, 4_100, "Sample location", null, 4.8, 26, null, previewOnly = true),
    RoadsideProvider("sample-4", "Roadside Auto Repair", ProviderCategory.MECHANIC, 6_300, "Sample location", null, 4.3, 59, null, previewOnly = true),
    RoadsideProvider("sample-5", "Emergency Fuel Delivery", ProviderCategory.FUEL, 8_700, "Sample location", null, 4.2, 18, null, previewOnly = true)
)

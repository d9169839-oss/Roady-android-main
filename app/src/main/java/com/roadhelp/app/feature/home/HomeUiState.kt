package com.roadhelp.app.feature.home

import com.roadhelp.app.domain.model.GeoPoint

/** Immutable presentation state for Home's foreground location action. */
data class HomeUiState(
    val isLoadingLocation: Boolean = false,
    val currentLocation: GeoPoint? = null,
    val locationMessage: String? = null
)

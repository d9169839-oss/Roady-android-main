package com.roadhelp.app.domain.model
/** Framework-free provider model. Nullable fields reflect incomplete external directory records. */
data class RoadsideProvider(
    val id: String,
    val name: String,
    val category: ProviderCategory,
    val distanceMeters: Int,
    val address: String?,
    val phoneDisplay: String?,
    val rating: Double?,
    val ratingCount: Int?,
    val openNow: Boolean?,
    val verified: Boolean = false,
    val previewOnly: Boolean = false
)

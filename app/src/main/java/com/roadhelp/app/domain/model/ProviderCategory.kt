package com.roadhelp.app.domain.model
/** Canonical category IDs shared with the backend API. ALL is a filter value, not a provider category. */
enum class ProviderCategory(val apiValue: String, val label: String, val symbol: String) {
    ALL("all", "All", "✦"),
    TOW("tow", "Towing", "↗"),
    TYRE("tyre", "Tyre", "◉"),
    MECHANIC("mechanic", "Mechanic", "⚙"),
    BATTERY("battery", "Battery", "ϟ"),
    FUEL("fuel", "Fuel", "⛽")
}

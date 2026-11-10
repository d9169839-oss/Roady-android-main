plugins {
    alias(libs.plugins.android.application) apply false
    // Kotlin language support is built into AGP 9+.
    alias(libs.plugins.kotlin.compose) apply false
}

package com.roadhelp.app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.Canvas
import com.roadhelp.app.domain.model.ProviderCategory
import com.roadhelp.app.feature.home.components.CategorySelector
import com.roadhelp.app.feature.home.components.EmergencyNoticeCard
import com.roadhelp.app.feature.home.components.FindNearbyHelpCard
import com.roadhelp.app.feature.home.components.HomeHeader
import com.roadhelp.app.feature.home.components.NearbyServicesHeader
import com.roadhelp.app.feature.home.components.OfflineStatusBanner
import com.roadhelp.app.feature.home.components.ProviderPreviewCard

/**
 * Home feature composition only.
 *
 * Reusable visual sections live in components/; screen-level state is limited
 * to the selected category until the discovery ViewModel is introduced.
 */
@Composable
fun HomeScreen(isOffline: Boolean) {
    var selectedCategory by remember { mutableStateOf(ProviderCategory.ALL) }
    val providers = remember { sampleProviders() }
    val visibleProviders = providers.filter {
        selectedCategory == ProviderCategory.ALL || it.category == selectedCategory
    }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            if (isOffline) OfflineStatusBanner()

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { HomeHeader() }
                item { FindNearbyHelpCard(onFindNearbyHelp = { /* Location flow is added in the location milestone. */ }) }
                item { EmergencyNoticeCard() }
                item {
                    CategorySelector(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                    )
                }
                item {
                    NearbyServicesHeader(
                        onShowAll = { selectedCategory = ProviderCategory.ALL }
                    )
                }
                items(visibleProviders, key = { it.id }) { provider ->
                    ProviderPreviewCard(provider = provider)
                }
            }
        }
    }
}

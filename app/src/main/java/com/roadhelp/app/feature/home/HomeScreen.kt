package com.roadhelp.app.feature.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.roadhelp.app.domain.model.ProviderCategory
import com.roadhelp.app.feature.home.components.CategorySelector
import com.roadhelp.app.feature.home.components.EmergencyNoticeCard
import com.roadhelp.app.feature.home.components.FindNearbyHelpCard
import com.roadhelp.app.feature.home.components.HomeHeader
import com.roadhelp.app.feature.home.components.LocationStatusMessage
import com.roadhelp.app.feature.home.components.NearbyServicesHeader
import com.roadhelp.app.feature.home.components.OfflineStatusBanner
import com.roadhelp.app.feature.home.components.ProviderPreviewCard

/**
 * Home feature composition.
 *
 * Runtime permission is requested only after the user taps Find nearby help.
 * Both approximate and precise location are accepted; no background permission
 * is requested or required.
 */
@Composable
fun HomeScreen(isOffline: Boolean, viewModel: HomeViewModel) {
    var selectedCategory by remember { mutableStateOf(ProviderCategory.ALL) }
    val providers = remember { sampleProviders() }
    val visibleProviders = providers.filter {
        selectedCategory == ProviderCategory.ALL || it.category == selectedCategory
    }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResults ->
        val granted = permissionResults[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissionResults[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) viewModel.onLocationPermissionGranted()
        else viewModel.onLocationPermissionDenied()
    }

    val requestNearbyHelp = {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            viewModel.onLocationPermissionGranted()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
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
                item {
                    FindNearbyHelpCard(
                        isLoading = uiState.isLoadingLocation,
                        onFindNearbyHelp = requestNearbyHelp
                    )
                }
                if (uiState.locationMessage != null) {
                    item { LocationStatusMessage(uiState.locationMessage!!) }
                }
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

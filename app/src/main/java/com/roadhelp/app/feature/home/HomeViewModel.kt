package com.roadhelp.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roadhelp.app.domain.repository.DeviceLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Coordinates Home's location action. Android permission prompts remain in the
 * Compose screen; this ViewModel only handles the result after permission is granted.
 */
class HomeViewModel(
    private val locationRepository: DeviceLocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onLocationPermissionGranted() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingLocation = true, locationMessage = null) }

            try {
                val point = locationRepository.getCurrentLocation()
                _uiState.update {
                    it.copy(
                        isLoadingLocation = false,
                        currentLocation = point,
                        locationMessage = if (point == null) {
                            "Couldn't determine your location. Check that device location is enabled and try again."
                        } else {
                            "Location found. Nearby provider search is the next step."
                        }
                    )
                }
            } catch (_: SecurityException) {
                _uiState.update {
                    it.copy(
                        isLoadingLocation = false,
                        locationMessage = "Location permission is required to find nearby help."
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingLocation = false,
                        locationMessage = "Couldn't get your location. Please try again."
                    )
                }
            }
        }
    }

    fun onLocationPermissionDenied() {
        _uiState.update {
            it.copy(
                isLoadingLocation = false,
                locationMessage = "Allow approximate or precise location to find roadside help near you."
            )
        }
    }

    class Factory(
        private val locationRepository: DeviceLocationRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(locationRepository) as T
        }
    }
}

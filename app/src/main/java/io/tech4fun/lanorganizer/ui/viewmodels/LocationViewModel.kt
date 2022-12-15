package io.tech4fun.lanorganizer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.tech4fun.lanorganizer.data.repository.LocationRepository
import io.tech4fun.lanorganizer.data.states.LocationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val locationRepository: LocationRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationUiState(0.0f,0.0f))

    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    private fun getSteamApps(){
        viewModelScope.launch {
            val locationResult = locationRepository.getLocation()
            //_uiState.emit(LocationUiState(latitude = locationResult., longitude = locationResult.longitude))
        }
    }
}
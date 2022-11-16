package io.tech4fun.lanorganizer.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import io.tech4fun.lanorganizer.data.states.LanUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class LanViewModel {
    private val _uiState = MutableStateFlow(LanUiState())

    val uiState: StateFlow<LanUiState> = _uiState.asStateFlow()

    fun changeName(name: String){
        //Stuff to save in data source
        _uiState.value = LanUiState(
            name = name,
            location = uiState.value.location,
            date = uiState.value.date
        )
    }

    fun changeLocation(location: String){
        //Stuff to save in data source
        _uiState.value = LanUiState(
            name = uiState.value.name,
            location = location,
            date = uiState.value.date
        )
    }

    fun changeDate(date: String){
        //Stuff to save in data source
        _uiState.value = LanUiState(
            name = uiState.value.name,
            location = uiState.value.name,
            date = date
        )
    }
}
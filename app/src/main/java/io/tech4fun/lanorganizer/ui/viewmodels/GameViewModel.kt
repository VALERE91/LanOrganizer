package io.tech4fun.lanorganizer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.tech4fun.lanorganizer.data.repository.GameRepository
import io.tech4fun.lanorganizer.data.states.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val gameRepository: GameRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(listOf<GameUiState>())

    val uiState: StateFlow<List<GameUiState>> = _uiState.asStateFlow()

    private fun getSteamApps(){
        viewModelScope.launch {
            try {
                val listResult = gameRepository.getSteamApps()
                _uiState.emit(listResult.map {
                    GameUiState(it.name, "")
                })
            }catch(e: Exception){

            }
        }
    }
}
package io.tech4fun.lanorganizer.ui.viewmodels

import android.text.Spannable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.repository.GameRepository
import io.tech4fun.lanorganizer.data.states.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as LanOrganizerApplication)
                val gameRpo = app.container.gameRepository
                GameViewModel(gameRpo)
            }
        }
    }
}
package io.tech4fun.lanorganizer.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.tech4fun.lanorganizer.data.states.GameUiState
import io.tech4fun.lanorganizer.ui.viewmodels.GameViewModel

@Composable
fun SelectGameScreen(modifier: Modifier = Modifier, viewModel: GameViewModel = viewModel(), onNextButtonClicked : (games: List<String>) -> Unit){
    val gameList = viewModel.uiState.collectAsState()

    Scaffold(
        content = {
            GameList(gameList = gameList.value, modifier.padding(it))
        }
    )
}

@Composable
private fun GameList(gameList: List<GameUiState>, modifier: Modifier = Modifier) {
    LazyColumn() {
        items(count = gameList.size,
            contentType = {
                it
            }){
            GameCard(game = gameList[it])
        }
    }
}

@Composable
private fun GameCard(game: GameUiState, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Row() {
            Text(text = game.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium)
        }
    }
}
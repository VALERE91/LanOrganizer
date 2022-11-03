package io.tech4fun.lanorganizer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.tech4fun.lanorganizer.R
import io.tech4fun.lanorganizer.data.states.LanUiState
import io.tech4fun.lanorganizer.ui.viewmodels.LanViewModel

@Composable
fun SelectGameScreen(modifier: Modifier = Modifier, lanUiState: LanViewModel, onNextButtonClicked : (games: List<String>) -> Unit){
    Column (modifier = modifier.fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(onClick = { onNextButtonClicked(listOf("CoD", "R6")) }) {
            Text(text = "Next")
        }
    }
}
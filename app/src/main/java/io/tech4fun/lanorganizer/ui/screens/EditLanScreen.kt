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
import io.tech4fun.lanorganizer.data.states.LanUiState

@Composable
fun EditLanScreen(modifier: Modifier = Modifier, lanUiState: LanUiState, onNextButtonClicked : (lanUiState: LanUiState) -> Unit){
    Column (modifier = modifier.fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(onClick = {onNextButtonClicked(lanUiState)}) {
            Text(text = "Select your games")
        }
    }
}
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

@Composable
fun DetailsLanScreen(modifier: Modifier = Modifier,
                     lanUiState: LanUiState,
                     onNextButtonClicked : () -> Unit,
                    onShareButtonClicked : () -> Unit){
    Column (modifier = modifier.fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(onClick = onNextButtonClicked) {
            Text(text = "Got it")
        }
        Button(onClick = onShareButtonClicked) {
            Text(text = "Share")
        }
    }
}
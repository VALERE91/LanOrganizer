package io.tech4fun.lanorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import io.tech4fun.lanorganizer.ui.screens.AppScreen
import io.tech4fun.lanorganizer.ui.theme.LanOrganizerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanOrganizerTheme {
                val windowSize = calculateWindowSizeClass(this)
                AppScreen(windowSize)
            }
        }
    }
}

package io.tech4fun.lanorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.tech4fun.lanorganizer.ui.screens.AddLanScreen
import io.tech4fun.lanorganizer.ui.screens.AppScreen
import io.tech4fun.lanorganizer.ui.theme.LanOrganizerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanOrganizerTheme {
                AppScreen()
            }
        }
    }
}

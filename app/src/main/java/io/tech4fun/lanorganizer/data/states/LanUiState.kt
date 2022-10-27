package io.tech4fun.lanorganizer.data.states

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*

data class LanUiState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val name: String = "",
    val date: String = "",
    val location: String = ""
)
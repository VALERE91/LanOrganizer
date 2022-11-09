package io.tech4fun.lanorganizer.data.models

import com.squareup.moshi.Json

data class GameModel (
    @Json(name = "name")
    val name: String,

    @Json(name = "appid")
    val steamAppId: Int
)
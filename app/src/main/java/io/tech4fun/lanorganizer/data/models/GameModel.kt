package io.tech4fun.lanorganizer.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "games")
data class GameModel (
    //@Transient
    @PrimaryKey
    var id: Int,

    @NonNull @ColumnInfo(name = "name")
   // @field:Json(name = "name")
    var name: String,

    @NonNull @ColumnInfo(name = "app_id")
   // @field:Json(name = "appid")
    var steamAppId: Int,

    @ColumnInfo(name = "image")
    // @field:Json(name = "image")
    var appImage: String
)
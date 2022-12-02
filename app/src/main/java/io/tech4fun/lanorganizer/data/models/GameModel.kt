package io.tech4fun.lanorganizer.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "games")
data class GameModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @NonNull @ColumnInfo(name = "name")
    var name: String,

    @Json(name = "app_id") @NonNull @ColumnInfo(name = "app_id")
    var steamAppId: Int,

    @ColumnInfo(name = "image") @Json(name = "image")
    var appImage: String
)
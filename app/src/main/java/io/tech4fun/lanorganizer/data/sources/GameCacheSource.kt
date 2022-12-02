package io.tech4fun.lanorganizer.data.sources

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.AppDatabase
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.repository.DefaultGameRepoEntryPoint
import io.tech4fun.lanorganizer.data.repository.GameSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.*


@Dao
interface GameDao{
    @Query("SELECT * FROM games")
    fun getAll(): LiveData<List<GameModel>>

    @Query("SELECT * FROM games WHERE name = :gameName")
    fun getByName(gameName: String): LiveData<GameModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: List<GameModel>)
}

object GameCacheSource : GameSource {

    private lateinit var appDatabase : AppDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    private var lastUpdateTime: Instant = Instant.now()

    init {
        val appContext = LanOrganizerApplication.getContext()
        val utilitiesEntryPoint =
            appContext?.let {
                EntryPointAccessors.fromApplication(
                    it, DefaultCacheGameSourceEntryPoint::class.java)
            }
        appDatabase = utilitiesEntryPoint?.appDatabase!!
    }

    val videos: LiveData<List<GameModel>> = appDatabase.gameDao().getAll()

    suspend fun refreshGames(){
        withContext(Dispatchers.IO){
            val games = GameOnlineSource.getGames()
            appDatabase.gameDao().insert(games)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getGames(): List<GameModel> {
        refreshGames()
        return appDatabase.gameDao().getAll().value!!
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultCacheGameSourceEntryPoint {
    var appDatabase: AppDatabase
}
package io.tech4fun.lanorganizer.data.sources

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.AppDatabase
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.repository.GameSource
import io.tech4fun.lanorganizer.work.RefreshDataWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.concurrent.TimeUnit


@Dao
interface GameDao{
    @Query("SELECT * FROM games")
    fun getAll(): Flow<List<GameModel>>

    @Query("SELECT * FROM games WHERE name = :gameName")
    fun getByName(gameName: String): Flow<GameModel>

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

        //Launch a periodic work to update the cache
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(LanOrganizerApplication.getContext()!!).enqueue(repeatingRequest);
    }

    suspend fun refreshGames(){
        withContext(Dispatchers.IO){
            val games = GameOnlineSource.getGames()
            appDatabase.gameDao().insert(games)
        }
    }

    override suspend fun getGames(): Flow<List<GameModel>> {
        //refreshGames()
        return appDatabase.gameDao().getAll()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultCacheGameSourceEntryPoint {
    var appDatabase: AppDatabase
}
package io.tech4fun.lanorganizer.data.repository

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.getInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.sources.GameCacheSource
import io.tech4fun.lanorganizer.data.sources.GameOnlineSource
import javax.inject.Inject
import javax.inject.Singleton

interface GameSource {
    suspend fun getGames(): List<GameModel>
}

interface GameRepository{
    suspend fun getSteamApps(): List<GameModel>
}

class DefaultGameRepository @Inject constructor(): GameRepository {

    private lateinit var gameSource : GameSource

    init {
        val appContext = LanOrganizerApplication.getContext()
        val utilitiesEntryPoint =
            appContext?.let {
                EntryPointAccessors.fromApplication(
                    it, DefaultGameRepoEntryPoint::class.java)
            }
        gameSource = utilitiesEntryPoint?.gameSource!!
    }

    override suspend fun getSteamApps(): List<GameModel> {
        return gameSource.getGames()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultGameRepoEntryPoint {
    var gameSource: GameSource
}

@InstallIn(SingletonComponent::class)
@Module
object GameRepositoryModule {
    @Singleton
    @Provides
    fun provideGameRepo(): GameRepository {
        return DefaultGameRepository()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object GameSourceModule {
    @Provides
    @Singleton
    fun provideGameSource(): GameSource {
        return GameCacheSource
    }
}
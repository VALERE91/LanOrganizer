package io.tech4fun.lanorganizer.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.sources.GameOnlineSource
import javax.inject.Inject

interface GameSource {
    suspend fun getGames(): List<GameModel>
}

interface GameRepository{
    suspend fun getSteamApps(): List<GameModel>
}

class DefaultGameRepository() : GameRepository {
    @Inject lateinit var gameSource: GameSource

    override suspend fun getSteamApps(): List<GameModel> {
        return gameSource.getGames()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object GameRepositoryModule {
    @Provides
    fun provideGameRepo(): GameRepository {
        return DefaultGameRepository()
    }
}
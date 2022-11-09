package io.tech4fun.lanorganizer.data.repository

import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.sources.GameOnlineSource

interface GameSource {
    suspend fun getGames(): List<GameModel>
}

interface GameRepository{
    suspend fun getSteamApps(): List<GameModel>
}

class DefaultGameRepository(val gameSource: GameSource) : GameRepository {
    override suspend fun getSteamApps(): List<GameModel> {
        return gameSource.getGames()
    }
}
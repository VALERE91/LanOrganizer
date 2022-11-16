package io.tech4fun.lanorganizer.data.sources

import com.squareup.moshi.Json
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.repository.DefaultGameRepository
import io.tech4fun.lanorganizer.data.repository.GameRepository
import io.tech4fun.lanorganizer.data.repository.GameSource
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import javax.inject.Singleton

object GameOnlineSource : GameSource {
    private const val BASE_URL = "http://api.steampowered.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()

    data class SteamAppList (
        @Json(name = "applist")
        val appList: SteamApps
    )

    data class SteamApps (
        @Json(name = "apps")
        val list: List<GameModel>
    )

    interface SteamAppsService{
        @GET("ISteamApps/GetAppList/v0002?format=json")
        suspend fun GetAppList() : SteamAppList
    }

    private val retrofitService: SteamAppsService by lazy {
        retrofit.create(SteamAppsService::class.java)
    }

    override suspend fun getGames(): List<GameModel> {
        return retrofitService.GetAppList().appList.list.map {
            GameModel(it.name, it.steamAppId)
        }
    }
}

@InstallIn(SingletonComponent::class)
@Module
object GameSourceModule {
    @Provides
    @Singleton
    fun provideGameSource(): GameSource {
        return GameOnlineSource
    }
}
package io.tech4fun.lanorganizer.data.sources

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.repository.DefaultGameRepository
import io.tech4fun.lanorganizer.data.repository.GameRepository
import io.tech4fun.lanorganizer.data.repository.GameSource
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import javax.inject.Singleton

object GameOnlineSource : GameSource {
    private const val BASE_URL = "http://localhost:8888"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    interface SteamAppsService{
        @GET("games")
        suspend fun GetAppList() : List<GameModel>
    }

    private val retrofitService: SteamAppsService by lazy {
        retrofit.create(SteamAppsService::class.java)
    }

    override suspend fun getGames(): List<GameModel> {
        return retrofitService.GetAppList().map {
            GameModel(0, it.name, it.steamAppId)
        }
    }
}
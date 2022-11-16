package io.tech4fun.lanorganizer.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.tech4fun.lanorganizer.data.repository.DefaultGameRepository
import io.tech4fun.lanorganizer.data.repository.GameRepository
import io.tech4fun.lanorganizer.data.repository.GameSource
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val gameSource: GameSource
    val gameRepository: GameRepository
}

class DefaultAppContainer : AppContainer {
    private companion object {
        private const val BASE_URL = "http://api.steampowered.com"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    override val gameSource: GameSource
        get() = TODO("Not yet implemented")

    override val gameRepository: GameRepository by lazy {
        DefaultGameRepository()
    }
}
package io.tech4fun.lanorganizer.data.sources

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.data.models.Location
import io.tech4fun.lanorganizer.data.repository.LocationSource
import javax.inject.Singleton

object LocationSource : LocationSource {
    override suspend fun getLocation(): Location {
        TODO()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object LocationSourceModule {
    @Provides
    @Singleton
    fun provideLocationSource(): LocationSource {
        return LocationSource
    }
}
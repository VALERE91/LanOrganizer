package io.tech4fun.lanorganizer.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.data.models.Location
import javax.inject.Inject

interface LocationSource {
    suspend fun getLocation(): Location
}

interface LocationRepository{
    suspend fun getLocation(): Location
}

class DefaultLocationRepository() : LocationRepository {
    @Inject
    lateinit var locationSource: LocationSource

    override suspend fun getLocation(): Location {
        return locationSource.getLocation()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object LocationRepositoryModule {
    @Provides
    fun provideLocationRpo(): LocationRepository {
        return DefaultLocationRepository()
    }
}
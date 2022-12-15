package io.tech4fun.lanorganizer.data.sources

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.livedata.LocationLiveData
import io.tech4fun.lanorganizer.data.models.LocationDetails
import io.tech4fun.lanorganizer.data.repository.LocationSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

object LocationSource : LocationSource {

    val liveData: LocationLiveData = LocationLiveData(LanOrganizerApplication.getContext()!!)

    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun getLocation(): Flow<LocationDetails> {
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
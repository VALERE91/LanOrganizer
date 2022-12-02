package io.tech4fun.lanorganizer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.models.GameModel
import io.tech4fun.lanorganizer.data.repository.GameSource
import io.tech4fun.lanorganizer.data.sources.GameCacheSource
import io.tech4fun.lanorganizer.data.sources.GameDao
import javax.inject.Singleton

@Database(entities = [GameModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "lan_organizer_database").build()
                    INSTANCE = instance

                instance
            }
        }
    }
}

@InstallIn(SingletonComponent::class)
@Module
object AppDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return AppDatabase.getDatabase(LanOrganizerApplication.getContext()!!)
    }
}
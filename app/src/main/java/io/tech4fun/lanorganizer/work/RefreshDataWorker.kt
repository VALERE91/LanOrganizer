package io.tech4fun.lanorganizer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.tech4fun.lanorganizer.LanOrganizerApplication
import io.tech4fun.lanorganizer.data.repository.DefaultGameRepoEntryPoint
import io.tech4fun.lanorganizer.data.repository.GameRepository
import io.tech4fun.lanorganizer.data.repository.GameSource

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "io.tech4fun.lanorganizer.work.RefreshDataWorker"
    }

    private var gameRepo : GameRepository

    init {
        val utilitiesEntryPoint =
            appContext.let {
                EntryPointAccessors.fromApplication(
                    it, DefaultRefreshWorkerEntryPoint::class.java)
            }
        gameRepo = utilitiesEntryPoint.gameRepo

    }

    override suspend fun doWork(): Result {
        try{
            gameRepo.getSteamApps();
        }catch (e: Exception){
            return Result.retry()
        }
        return Result.success()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultRefreshWorkerEntryPoint {
    var gameRepo : GameRepository
}

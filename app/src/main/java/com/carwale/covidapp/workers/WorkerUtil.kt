package com.carwale.covidapp.workers

import androidx.work.*
import com.carwale.covidapp.BaseApp
import java.util.concurrent.TimeUnit

class WorkerUtil {
    companion object {
        private val workManager = WorkManager.getInstance(BaseApp.instance.ctx)
        private val connectionConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

//        fun initializeHourlySync() {
//            workManager.enqueueUniquePeriodicWork(
//                TIME_SYNC,
//                ExistingPeriodicWorkPolicy.REPLACE,
//                PeriodicWorkRequest.Builder(
//                    HourlySyncWorker::class.java,
//                    5,
//                    TimeUnit.MINUTES
//                ).addTag(TIME_SYNC).build()
//            )
//        }
    }
}
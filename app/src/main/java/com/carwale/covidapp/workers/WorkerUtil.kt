package com.carwale.covidapp.workers

import androidx.work.*
import com.carwale.covidapp.BaseApp

class WorkerUtil {
    companion object {
        private val workManager = WorkManager.getInstance(BaseApp.instance.ctx)
        private val connectionConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

//        fun initializeNotificationWorker(keys: Map<String, String>) {
//            val data = Data.Builder()
//            data.putAll(keys)
//            workManager.enqueue(
//                OneTimeWorkRequest.Builder(NotificationWorker::class.java)
//                    .addTag(NOTIFICATION_SYNC)
//                    .setConstraints(connectionConstraints)
//                    .setInputData(data.build())
//                    .build()
//            )
//        }
    }
}
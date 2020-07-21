package com.carwale.covidapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.carwale.covidapp.utils.shared_prefrence.SharedPref

class HourlySyncWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val sharedPref = SharedPref.getInstance()

//        whenOnline {
//            if (it == null) {
//                sharedPref.setLongPreference(Constants.SharedPrefTags.LAST_ONLINE_TIME, Date().time)
//                sharedPref.setBooleanPreference(Constants.SharedPrefTags.IS_OFFLINE_ALLOWED, true)
//            } else {
//                val lastOnlineTime =
//                    sharedPref.getLongPreference(Constants.SharedPrefTags.LAST_ONLINE_TIME, Date().time)
//                val diff = Date().time - lastOnlineTime
//                sharedPref.setBooleanPreference(
//                    Constants.SharedPrefTags.IS_OFFLINE_ALLOWED,
//                    diff <= OFFLINE_LIMIT
//                )
//            }
//        }

        return Result.success()
    }
}
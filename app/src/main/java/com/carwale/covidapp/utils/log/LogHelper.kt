package com.carwale.covidapp.utils.log

import android.content.Context
import android.util.Log

class LogHelper {

    companion object {

        private const val TAG_DATA: String = "PLog"
        private const val TAG_CONTEXT_DATA: String = "PContextLog"
        private const val TAG_ERROR: String = "PErrorLog"

        fun showLogData(message: String){
            Log.d(TAG_DATA,message)
        }


        fun showLogError(message: String?) {
            Log.d(TAG_ERROR, message ?: "")
        }

        fun showContextLogData(context: Context, s: String) {
            Log.d(TAG_CONTEXT_DATA, String.format("%s -- %s", context.javaClass.canonicalName, s))
        }
    }
}
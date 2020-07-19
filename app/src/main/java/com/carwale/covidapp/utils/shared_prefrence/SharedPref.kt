package com.carwale.covidapp.utils.shared_prefrence

import android.content.Context
import android.content.SharedPreferences
import com.carwale.covidapp.BaseApp
import com.carwale.covidapp.utils.Constants

class SharedPref private constructor() {

    companion object {

        var sharePref = SharedPref()
        lateinit var mSp: SharedPreferences


        fun getInstance(): SharedPref {
            if (!Companion::mSp.isInitialized) {
                synchronized(sharePref::class.java) {
                    if (!Companion::mSp.isInitialized) {
                        mSp = BaseApp.instance.getSharedPreferences(
                            Constants.SharedPrefTags.myPrefKey,
                            Context.MODE_PRIVATE
                        )
                    }
                }
            }
            return sharePref
        }
    }


    fun deletAll() {
        mSp.edit()?.clear()?.apply()
    }

    fun deleteSpecific(key: String) {
        mSp.edit().remove(key).apply()
    }

/*------*-*------ Default Generice methods* ------*-*------ */

    fun setStringPreference(key: String, value: String) {
        mSp.edit()?.apply {
            putString(key, value)
            apply()
        }
    }

    fun setLongPreference(key: String, value: Long) {
        mSp.edit()?.apply {
            putLong(key, value)
            apply()
        }
    }

    fun setDoublePreference(key: String, value: Double) {
        mSp.edit()?.apply {
            putLong(key, java.lang.Double.doubleToRawLongBits(value))
            apply()
        }
    }

    fun setIntPreference(key: String, value: Int) {
        mSp.edit()?.apply {
            putInt(key, value)
            apply()
        }
    }

    fun setBooleanPreference(key: String, value: Boolean) {
        mSp.edit()?.apply {
            putBoolean(key, value)
            apply()
        }
    }


    fun getStringPreference(key: String, value: String = ""): String {
        return mSp.getString(key, value) ?: ""
    }

    fun getIntPreference(key: String, value: Int = 0): Int {
        return mSp.getInt(key, value)
    }

    fun getBooleanPreference(key: String, value: Boolean = false): Boolean {
        return mSp.getBoolean(key, value)
    }

    fun getLongPreference(key: String, value: Long = 0L): Long {
        return mSp.getLong(key, value)
    }

    fun getDoublePreference(key: String, value: Double = 0.0): Double {
        return java.lang.Double.longBitsToDouble(
            mSp.getLong(
                key,
                java.lang.Double.doubleToRawLongBits(value)
            )
        )
    }

    fun setPreferences(key: String, value: Any) {
        when (value) {
            is String -> setStringPreference(key, value)
            is Int -> setIntPreference(key, value)
            is Boolean -> setBooleanPreference(key, value)
        }
    }

    fun getPreferences(key: String, value: Any): Any? {

        when (value) {
            is String -> return getStringPreference(key, value)
            is Int -> return getIntPreference(key, value)
            is Boolean -> return getBooleanPreference(key, value)
        }
        return null
    }
}
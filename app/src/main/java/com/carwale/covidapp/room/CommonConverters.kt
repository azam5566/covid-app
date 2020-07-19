package com.carwale.covidapp.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class CommonConverters {

    @TypeConverter
    fun toNullableStringList(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromNullableStringList(list: List<String?>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toCalender(value: Long?): Calendar? {
        return value?.let {
            val cal = Calendar.getInstance()
            cal.time = Date(it)
            cal
        }
    }

    @TypeConverter
    fun fromCalender(date: Calendar?): Long? {
        return date?.time?.time
    }

    @TypeConverter
    fun fromArrayStringToString(value: String): Array<String?>? {
        val listType: Type = object : TypeToken<Array<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringToStringArrayList(list: Array<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromArrayString(value: String): Array<Int?>? {
        val listType: Type = object : TypeToken<Array<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringArrayList(list: Array<Int?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}
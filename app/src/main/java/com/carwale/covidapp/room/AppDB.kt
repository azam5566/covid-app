package com.carwale.covidapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.carwale.covidapp.models.local.CountriesData
import com.carwale.covidapp.models.local.GlobalData

@Database(
    entities = [GlobalData::class, CountriesData::class],
    version = 1
)
@TypeConverters(CommonConverters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}

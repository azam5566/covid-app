package com.carwale.covidapp.di.modules

import android.content.Context
import androidx.room.Room
import com.carwale.covidapp.room.AppDB
import com.carwale.covidapp.room.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDb(context: Context): AppDB {
        return Room.databaseBuilder(context, AppDB::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDB: AppDB): UserDao = appDB.userDao()
}

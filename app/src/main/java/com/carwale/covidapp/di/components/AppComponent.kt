package com.carwale.covidapp.di.components

import android.app.Application
import android.content.SharedPreferences
import com.carwale.covidapp.di.modules.ApiModule
import com.carwale.covidapp.di.modules.AppModule
import com.carwale.covidapp.di.modules.DatabaseModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {

    fun provideApplication(): Application
    fun provideSharedPrefs(): SharedPreferences

    fun getActivityComponentBuilder(): ActivityComponent.Builder

}
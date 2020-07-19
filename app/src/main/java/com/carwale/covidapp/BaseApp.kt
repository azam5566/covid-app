package com.carwale.covidapp

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.carwale.covidapp.api.UserService
import com.carwale.covidapp.di.components.AppComponent
import com.carwale.covidapp.di.components.DaggerAppComponent
import com.carwale.covidapp.di.modules.AppModule
import com.carwale.covidapp.room.AppDB
import com.carwale.covidapp.room.UserDao
import javax.inject.Inject

class BaseApp : Application() {
    @Inject
    internal lateinit var ctx: Context

    //  Retrofit Services
    @Inject
    internal lateinit var userService : UserService

    // Room Database Dao
    @Inject
    internal lateinit var appDB: AppDB
    @Inject
    internal lateinit var userDao: UserDao

    lateinit var component: AppComponent


    companion object {
        lateinit var instance: BaseApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        component = createComponent()
        component.getActivityComponentBuilder().build().inject(this)
        Stetho.initializeWithDefaults(this)

    }

    fun createComponent() = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    fun getAppComponent() = component
}
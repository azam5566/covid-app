package com.carwale.covidapp.base

import androidx.lifecycle.ViewModel
import com.carwale.covidapp.BaseApp


open class BaseViewModel : ViewModel()                       {

    val userService = BaseApp.instance.userService

    val appDB = BaseApp.instance.appDB
    val userDao = BaseApp.instance.userDao
}
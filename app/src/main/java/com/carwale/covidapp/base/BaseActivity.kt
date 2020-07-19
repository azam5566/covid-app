package com.carwale.covidapp.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.carwale.covidapp.BaseApp
import com.carwale.covidapp.api.UserService
import com.carwale.covidapp.di.components.AppComponent
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.room.AppDB
import com.carwale.covidapp.room.UserDao

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V
    private lateinit var mContext: Context
    private lateinit var mManager: FragmentManager

    internal lateinit var ctx: Context
    internal lateinit var mSP: SharedPref

    internal lateinit var appDB: AppDB
    internal lateinit var userDao: UserDao

    internal lateinit var userService: UserService

    internal lateinit var component: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration = resources.configuration
        configuration.fontScale = 1f
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        metrics.scaledDensity = configuration.fontScale * metrics.density
        configuration.densityDpi = resources.displayMetrics.densityDpi
        baseContext.resources.updateConfiguration(configuration, metrics)

        mBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutId())
        mViewModel = ViewModelProvider(this@BaseActivity).get(getViewModel())
        mManager = supportFragmentManager
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mContext = this@BaseActivity

        component = BaseApp.instance.getAppComponent()
        ctx = BaseApp.instance.ctx
        mSP = SharedPref.getInstance()

        appDB = BaseApp.instance.appDB
        userDao = BaseApp.instance.userDao

        userService = BaseApp.instance.userService


        onBinding()
        viewModelListener()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        /* when (item!!.itemId) {
             android.R.id.home -> onBackPressed()
         }*/
        return super.onOptionsItemSelected(item)
    }

}

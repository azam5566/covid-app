package com.carwale.covidapp.views

import android.content.Intent
import android.os.Handler
import com.carwale.covidapp.R
import com.carwale.covidapp.base.BaseActivity
import com.carwale.covidapp.databinding.ActivitySplashBinding
import com.carwale.covidapp.models.DataResponse
import com.carwale.covidapp.utils.Constants
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.views.dashboard.DashboardActivity
import com.carwale.covidapp.views.locations.MapsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModel() = SplashViewModel::class.java

    override fun onBinding() {
        Handler().postDelayed({
            nextScreen()
        }, 2 * 1000)

        mViewModel.callApi()

    }

    override fun viewModelListener() {
    }

    private fun nextScreen() {
        val country = SharedPref.getInstance().getStringPreference(Constants.Location.COUNTRY_NAME)
        if (country.isNotEmpty()){
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        else{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}
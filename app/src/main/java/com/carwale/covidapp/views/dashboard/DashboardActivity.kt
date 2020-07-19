package com.carwale.covidapp.views.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carwale.covidapp.R
import com.carwale.covidapp.base.BaseActivity
import com.carwale.covidapp.databinding.ActivityDashboardBinding
import com.carwale.covidapp.utils.Constants
import com.carwale.covidapp.utils.extensions.showMessage
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.views.dashboard.adapters.CountryListAdapter
import com.carwale.covidapp.views.locations.MapsActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>() {
    private lateinit var countryListAdapter: CountryListAdapter
    var listSortedBy = ""
    override fun getLayoutId() = R.layout.activity_dashboard

    override fun getViewModel() = DashboardViewModel::class.java

    override fun onBinding() {
        countryListAdapter = CountryListAdapter(this)
        val pref = SharedPref.getInstance()

        userDao.getLiveDataTotalCases().observe(this, Observer {
            mBinding.totalCases.text = it.toString()
        })

        userDao.getLiveDataTotalDeaths().observe(this, Observer {
            mBinding.totalDeaths.text = it.toString()
        })

        userDao.getLiveDataTotalRecovered().observe(this, Observer {
            mBinding.totalRecov.text = it.toString()
        })

        mBinding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@DashboardActivity)
            recyclerView.adapter = countryListAdapter
        }

        mBinding.changeLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("fromApp", "fromApp")
            startActivity(intent)
        }

        userDao.getAllData().observe(this, Observer {
            val (myCountryData, allOther) = it.partition { data ->
                data.Country == SharedPref.getInstance()
                    .getStringPreference(Constants.Location.COUNTRY_NAME)
            }
//            countryListAdapter.populate(it.toMutableList())
            countryListAdapter.populate((myCountryData + allOther).toMutableList(), false)
        })

        mBinding.figure1.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == "descending"){
                userDao.getAllDataSortCountryASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "ascending"
                })
            }
            else{
                userDao.getAllDataSortCountryDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "descending"
                })
            }
        }

        mBinding.figure2.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == "descending"){
                userDao.getAllDataSortCasesASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "ascending"
                })
            }
            else{
                userDao.getAllDataSortCasesDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "descending"
                })
            }
        }

        mBinding.figure3.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == "descending"){
                userDao.getAllDataSortDeathsASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "ascending"
                })
            }
            else{
                userDao.getAllDataSortDeathsDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "descending"
                })
            }
        }

        mBinding.figure4.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == "descending"){
                userDao.getAllDataSortRecoveredASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "ascending"
                })
            }
            else{
                userDao.getAllDataSortRecoveredDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = "descending"
                })
            }
        }



        val dialogView =
            layoutInflater.inflate(R.layout.dailog_filter, null)

        val dialogBuilder = MaterialAlertDialogBuilder(this)
            .setTitle("Filter By")
            .setView(dialogView)
            .setNegativeButton("Close", null)
            .setPositiveButton("Filter", null)
            .create()

        dialogBuilder.setOnShowListener {
            var casesStart : Long? = null
            var casesEnd : Long? = null

            var deathStart : Long? = null

            var deathEnd : Long? = null
            var recovStart : Long? = null

            var recovEnd : Long? = null

            dialogBuilder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                if (!dialogView?.findViewById<EditText>(R.id.cases_start)?.text.isNullOrEmpty()){
                    casesStart = dialogView?.findViewById<EditText>(R.id.cases_start)?.text.toString().trim().toLong()
                }
                if (!dialogView?.findViewById<EditText>(R.id.cases_end)?.text.isNullOrEmpty()){
                    casesEnd = dialogView?.findViewById<EditText>(R.id.cases_end)?.text.toString().trim().toLong()
                }
                if (!dialogView?.findViewById<EditText>(R.id.death_start)?.text.isNullOrEmpty()){
                    deathStart = dialogView?.findViewById<EditText>(R.id.death_start)?.text.toString().trim().toLong()
                }
                if (!dialogView?.findViewById<EditText>(R.id.death_end)?.text.isNullOrEmpty()){
                    deathEnd = dialogView?.findViewById<EditText>(R.id.death_end)?.text.toString().trim().toLong()
                }
                if (!dialogView?.findViewById<EditText>(R.id.recv_start)?.text.isNullOrEmpty()){
                    recovStart = dialogView?.findViewById<EditText>(R.id.recv_start)?.text.toString().trim().toLong()
                }
                if (!dialogView?.findViewById<EditText>(R.id.recv_end)?.text.isNullOrEmpty()){
                    recovEnd = dialogView?.findViewById<EditText>(R.id.recv_end)?.text.toString().trim().toLong()
                }

                if(casesStart != null || deathStart != null || recovStart != null){
                    if (casesStart != null){

                    }
                }
                else{
                    Snackbar.make(dialogView.rootView, "Please enter filter range", Snackbar.LENGTH_SHORT)
                        .show()
                }

                userDao.getAllDataFiltered(casesStart, casesEnd, deathStart, deathEnd,  recovStart, recovEnd).observe(this, Observer {

                    mBinding.recyclerView.removeAllViewsInLayout()
                    countryListAdapter.populate(it.toMutableList(), false)
                    dialogBuilder.dismiss()
                })
            }
        }

        mBinding.filterBy.setOnClickListener {
            dialogBuilder.show()
        }
    }

    override fun viewModelListener() {
    }
}
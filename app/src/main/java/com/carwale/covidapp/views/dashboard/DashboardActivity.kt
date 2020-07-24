package com.carwale.covidapp.views.dashboard

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carwale.covidapp.R
import com.carwale.covidapp.base.BaseActivity
import com.carwale.covidapp.databinding.ActivityDashboardBinding
import com.carwale.covidapp.utils.Constants
import com.carwale.covidapp.utils.extensions.READABLE_FORMAT_WITH_DATE
import com.carwale.covidapp.utils.extensions.toDateFormat
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.views.dashboard.adapters.CountryListAdapter
import com.carwale.covidapp.views.locations.MapsActivity
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.annotations.TestOnly

class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>() {
    private lateinit var countryListAdapter: CountryListAdapter
    private var listSortedBy = ""
    override fun getLayoutId() = R.layout.activity_dashboard

    override fun getViewModel() = DashboardViewModel::class.java

    @SuppressLint("SetTextI18n")
    override fun onBinding() {
        countryListAdapter = CountryListAdapter(this)

        userDao.getLiveDataTotal().observe(this, Observer {
            mBinding.totalCases.text = it.sumCases.toString()
            mBinding.totalDeaths.text = it.sumDeaths.toString()
            mBinding.totalRecov.text = it.sumRecov.toString()
        })

        userDao.getLiveDataGlobalData().observe(this, Observer {
            mBinding.lastUpdated.text = "Last updated at " + it?.lastApiCalled?.toDateFormat(READABLE_FORMAT_WITH_DATE)
        })

        mBinding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@DashboardActivity)
            recyclerView.adapter = countryListAdapter
            header.text = SharedPref.getInstance().getStringPreference(Constants.Location.COUNTRY_NAME) + " (" + SharedPref.getInstance().getStringPreference(Constants.Location.COUNTRY_CODE) + ")"
        }

        mBinding.changeLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("fromApp", "fromApp")
            startActivity(intent)
        }

        userDao.getAllData().observe(this, Observer {
            val (myCountryData, allOther) = it.partition { data ->
                data.Country.contains(SharedPref.getInstance()
                    .getStringPreference(Constants.Location.COUNTRY_NAME))
            }
            countryListAdapter.populate((myCountryData + allOther).toMutableList(), false)
        })

        mBinding.figure1.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == Constants.Range.DESCENDING){
                userDao.getAllDataSortCountryASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.ASCENDING
                })
            }
            else{
                userDao.getAllDataSortCountryDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.DESCENDING
                })
            }
        }

        mBinding.figure2.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == Constants.Range.DESCENDING){
                userDao.getAllDataSortCasesASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.ASCENDING
                })
            }
            else{
                userDao.getAllDataSortCasesDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.DESCENDING
                })
            }
        }

        mBinding.figure3.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == Constants.Range.DESCENDING){
                userDao.getAllDataSortDeathsASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.ASCENDING
                })
            }
            else{
                userDao.getAllDataSortDeathsDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.DESCENDING
                })
            }
        }

        mBinding.figure4.setOnClickListener {
            mBinding.recyclerView.removeAllViewsInLayout()

            if (listSortedBy == Constants.Range.DESCENDING){
                userDao.getAllDataSortRecoveredASC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.ASCENDING
                })
            }
            else{
                userDao.getAllDataSortRecoveredDESC().observe(this, Observer {
                    countryListAdapter.populate(it.toMutableList(), true)
                    listSortedBy = Constants.Range.DESCENDING
                })
            }
        }



        val dialogView =
            layoutInflater.inflate(R.layout.dailog_filter, null)

        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
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

                //filter input validations
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

                val chipCases = layoutInflater.inflate(
                    R.layout.chip_layout,
                    mBinding.chipGroup,
                    false
                ) as Chip
                chipCases.setChipBackgroundColorResource(R.color.c_yellow_dark)
                chipCases.setTextColor(ContextCompat.getColor(this, R.color.c_yellow))
                val chipDeath = layoutInflater.inflate(
                    R.layout.chip_layout,
                    mBinding.chipGroup,
                    false
                ) as Chip
                chipDeath.setChipBackgroundColorResource(R.color.c_purple_dark)
                chipDeath.setTextColor(ContextCompat.getColor(this, R.color.c_purple))
                val chipRecov = layoutInflater.inflate(
                    R.layout.chip_layout,
                    mBinding.chipGroup,
                    false
                ) as Chip
                chipRecov.setChipBackgroundColorResource(R.color.c_green_dark)
                chipRecov.setTextColor(ContextCompat.getColor(this, R.color.c_green))

                chipCases.setOnClickListener {
                    dialogBuilder.show()
                }
                chipDeath.setOnClickListener {
                    dialogBuilder.show()
                }
                chipRecov.setOnClickListener {
                    dialogBuilder.show()
                }

                //filter input validations
                if(casesStart != null || deathStart != null || recovStart != null){
                    if (casesStart != null && deathStart != null && recovStart != null){
                        if (casesStart != null && deathStart != null && recovStart != null && casesEnd != null && deathEnd != null && recovEnd != null){
                        // all fields search
                            userDao.filterAll(casesStart, casesEnd, deathStart, deathEnd, recovStart, recovEnd).observe(this, Observer { list ->
                                mBinding.recyclerView.removeAllViewsInLayout()
                                countryListAdapter.populate(list.toMutableList(), false)
                                dialogBuilder.dismiss()
                            })
                            mBinding.chipGroup.removeAllViewsInLayout()

                            chipCases.text = casesStart.toString() + " - " + casesEnd.toString()
                            chipDeath.text = deathStart.toString() + " - " + deathEnd.toString()
                            chipRecov.text = recovStart.toString() + " - " + recovEnd.toString()

                            mBinding.chipGroup.addView(chipCases)
                            mBinding.chipGroup.addView(chipDeath)
                            mBinding.chipGroup.addView(chipRecov)

                            mBinding.filterBy.visibility = View.GONE
                            mBinding.cardView.visibility = View.VISIBLE
                        }
                        else{
                            Snackbar.make(it, "Please enter proper filter range", Snackbar.LENGTH_SHORT).setTextColor(ContextCompat.getColor(this, R.color.white)).show()
                        }

                    }
                    else if(casesStart != null && deathStart != null){
                        if (casesStart != null && deathStart != null && casesEnd != null && deathEnd != null){
                            //2
                            userDao.filterTotalCasesDeaths(casesStart,casesEnd,deathStart, deathEnd).observe(this, Observer { list ->
                                mBinding.recyclerView.removeAllViewsInLayout()
                                countryListAdapter.populate(list.toMutableList(), false)
                                dialogBuilder.dismiss()
                            })
                            mBinding.chipGroup.removeAllViewsInLayout()

                            chipCases.text = casesStart.toString() + " - " + casesEnd.toString()
                            chipDeath.text = deathStart.toString() + " - " + deathEnd.toString()

                            mBinding.chipGroup.addView(chipCases)
                            mBinding.chipGroup.addView(chipDeath)

                            mBinding.filterBy.visibility = View.GONE
                            mBinding.cardView.visibility = View.VISIBLE
                        }
                        else{
                            Snackbar.make(it, "Please enter proper filter range", Snackbar.LENGTH_SHORT).setTextColor(ContextCompat.getColor(this, R.color.white)).show()
                        }

                    }
                    else if(casesStart != null && recovStart != null){
                        if (casesStart != null && recovStart != null && casesEnd != null && recovEnd != null){
                            //2
                            userDao.filterTotalCasesRecov(casesStart,casesEnd,recovStart, recovEnd).observe(this, Observer { list ->
                                mBinding.recyclerView.removeAllViewsInLayout()
                                countryListAdapter.populate(list.toMutableList(), false)
                                dialogBuilder.dismiss()
                            })
                            mBinding.chipGroup.removeAllViewsInLayout()

                            chipCases.text = casesStart.toString() + " - " + casesEnd.toString()
                            chipRecov.text = recovStart.toString() + " - " + recovEnd.toString()

                            mBinding.chipGroup.addView(chipCases)
                            mBinding.chipGroup.addView(chipRecov)

                            mBinding.filterBy.visibility = View.GONE
                            mBinding.cardView.visibility = View.VISIBLE
                        }
                        else{
                            Snackbar.make(it, "Please enter proper filter range", Snackbar.LENGTH_SHORT).setTextColor(ContextCompat.getColor(this, R.color.white)).show()
                        }

                    }
                    else if(deathStart != null && recovStart != null){
                        if (deathStart != null && recovStart != null && deathEnd != null && recovEnd != null){
                            //2
                            userDao.filterTotalRecovDeaths(deathStart,deathEnd,recovStart, recovEnd).observe(this, Observer { list ->
                                mBinding.recyclerView.removeAllViewsInLayout()
                                countryListAdapter.populate(list.toMutableList(), false)
                                dialogBuilder.dismiss()
                            })

                            mBinding.chipGroup.removeAllViewsInLayout()
                            chipDeath.text = deathStart.toString() + " - " + deathEnd.toString()
                            chipRecov.text = recovStart.toString() + " - " + recovEnd.toString()

                            mBinding.chipGroup.addView(chipDeath)
                            mBinding.chipGroup.addView(chipRecov)

                            mBinding.filterBy.visibility = View.GONE
                            mBinding.cardView.visibility = View.VISIBLE
                        }
                        else{
                            Snackbar.make(it, "Please enter proper filter range", Snackbar.LENGTH_SHORT).setTextColor(ContextCompat.getColor(this, R.color.white)).show()
                        }

                    }
                    else if(deathStart != null && deathEnd != null){
                        //1
                        userDao.filterTotalDeaths(deathStart, deathEnd).observe(this, Observer { list ->
                            mBinding.recyclerView.removeAllViewsInLayout()
                            countryListAdapter.populate(list.toMutableList(), false)
                            dialogBuilder.dismiss()
                        })
                        mBinding.chipGroup.removeAllViewsInLayout()
                        chipDeath.text = deathStart.toString() + " - " + deathEnd.toString()

                        mBinding.chipGroup.addView(chipDeath)

                        mBinding.filterBy.visibility = View.GONE
                        mBinding.cardView.visibility = View.VISIBLE

                    }
                    else if(casesStart != null && casesEnd != null){
                        //1
                        userDao.filterTotalConfirmed(casesStart, casesEnd).observe(this, Observer { list ->
                            mBinding.recyclerView.removeAllViewsInLayout()
                                countryListAdapter.populate(list.toMutableList(), false)
                                dialogBuilder.dismiss()
                            })
                        mBinding.chipGroup.removeAllViewsInLayout()
                        chipCases.text = casesStart.toString() + " - " + casesEnd.toString()

                        mBinding.chipGroup.addView(chipCases)

                        mBinding.filterBy.visibility = View.GONE
                        mBinding.cardView.visibility = View.VISIBLE

                    }
                    else if(recovStart != null && recovEnd != null){
                        //1
                        userDao.filterTotalRecov(recovStart, recovEnd).observe(this, Observer { list ->
                            mBinding.recyclerView.removeAllViewsInLayout()
                            countryListAdapter.populate(list.toMutableList(), false)
                            dialogBuilder.dismiss()
                        })
                        mBinding.chipGroup.removeAllViewsInLayout()
                        chipRecov.text = recovStart.toString() + " - " + recovEnd.toString()

                        mBinding.chipGroup.addView(chipRecov)

                        mBinding.filterBy.visibility = View.GONE
                        mBinding.cardView.visibility = View.VISIBLE

                    }
                    else{
                        Snackbar.make(it, "Please enter proper filter range", Snackbar.LENGTH_SHORT).setTextColor(ContextCompat.getColor(this, R.color.white)).show()
                    }
                }
                else{
                    Snackbar.make(it, "Please enter filter range", Snackbar.LENGTH_SHORT).setTextColor(ContextCompat.getColor(this, R.color.white)).show()
                }
            }
        }

        mBinding.filterBy.setOnClickListener {
            dialogBuilder.show()
        }

        mBinding.cardView.setOnClickListener {
            mBinding.chipGroup.removeAllViewsInLayout()
            mBinding.filterBy.visibility = View.VISIBLE
            mBinding.cardView.visibility = View.GONE
            userDao.getAllData().observe(this, Observer {
                val (myCountryData, allOther) = it.partition { data ->
                    data.Country == SharedPref.getInstance()
                        .getStringPreference(Constants.Location.COUNTRY_NAME)
                }
                countryListAdapter.populate((myCountryData + allOther).toMutableList(), false)
            })
        }

        mViewModel.callApi()
    }

    override fun viewModelListener() {
    }

    @Suppress("unused", "UNUSED_VARIABLE")
    @TestOnly
    fun setTestViewModel(testViewModel: DashboardViewModel) {
        val viewModel = testViewModel
    }
}
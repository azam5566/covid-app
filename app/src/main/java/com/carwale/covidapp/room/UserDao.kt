package com.carwale.covidapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carwale.covidapp.models.TotalData
import com.carwale.covidapp.models.local.CountriesData
import com.carwale.covidapp.models.local.GlobalData

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGlobalData(globalData: GlobalData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountriesData(countryData : List<CountriesData>)

    @Query("SELECT SUM(totalConfirmed) as sumCases, SUM(totalDeaths) as sumDeaths, SUM(totalRecovered) as sumRecov FROM countries_data")
    fun getLiveDataTotal(): LiveData<TotalData>

    @Query("SELECT * FROM global_data")
    fun getLiveDataGlobalData(): LiveData<GlobalData>

    @Query("SELECT * FROM countries_data WHERE totalConfirmed > 0 ORDER BY totalConfirmed DESC")
    fun getAllData(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE totalConfirmed > 0 and Country NOT IN (:country)")
    fun getAllDataExcept(country : String): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE Country =:country")
    fun getMyCountry(country : String): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY Country DESC")
    fun getAllDataSortCountryDESC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY Country ASC")
    fun getAllDataSortCountryASC(): LiveData<List<CountriesData>>


    @Query("SELECT * FROM countries_data ORDER BY totalConfirmed DESC")
    fun getAllDataSortCasesDESC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY totalConfirmed ASC")
    fun getAllDataSortCasesASC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY totalDeaths DESC")
    fun getAllDataSortDeathsDESC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY totalDeaths ASC")
    fun getAllDataSortDeathsASC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY totalRecovered DESC")
    fun getAllDataSortRecoveredDESC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data ORDER BY totalRecovered ASC")
    fun getAllDataSortRecoveredASC(): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE (totalConfirmed <= :casesEnd and totalConfirmed >= :casesStart) and (totalDeaths >= :deathStart and totalDeaths <= :deathEnd) and (totalRecovered >= :recovStart and totalRecovered <= :recovEnd) ORDER BY totalConfirmed ASC ")
    fun filterAll(casesStart : Long?, casesEnd : Long?,deathStart : Long?, deathEnd : Long?, recovStart : Long?, recovEnd : Long?): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE totalConfirmed >= :casesStart and totalConfirmed <= :casesEnd ORDER BY totalConfirmed ASC")
    fun filterTotalConfirmed(casesStart : Long?, casesEnd : Long?): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE totalDeaths >= :deathStart and totalDeaths <= :deathEnd ORDER BY totalDeaths ASC")
    fun filterTotalDeaths(deathStart: Long?, deathEnd: Long?): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE totalRecovered >= :recovStart and totalRecovered <= :recovEnd ORDER BY totalRecovered ASC")
    fun filterTotalRecov(recovStart: Long?, recovEnd: Long?): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE (totalConfirmed >= :casesStart and totalConfirmed <= :casesEnd) and (totalRecovered >= :deathStart and totalRecovered <= :deathEnd) ORDER BY totalRecovered ASC")
    fun filterTotalCasesDeaths(casesStart: Long?, casesEnd: Long?, deathStart: Long?, deathEnd: Long?): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE (totalConfirmed >= :casesStart and totalConfirmed <= :casesEnd) and (totalRecovered >= :recovStart and totalRecovered <= :recovEnd) ORDER BY totalRecovered ASC")
    fun filterTotalCasesRecov(casesStart: Long?, casesEnd: Long?, recovStart: Long?, recovEnd: Long?): LiveData<List<CountriesData>>

    @Query("SELECT * FROM countries_data WHERE (totalRecovered >= :recovStart and totalRecovered <= :recovEnd) and ( totalDeaths >= :deathStart and totalDeaths <= :deathEnd) ORDER BY totalRecovered ASC")
    fun filterTotalRecovDeaths(deathStart: Long?, deathEnd: Long?, recovStart: Long?, recovEnd: Long?): LiveData<List<CountriesData>>


    //deathStart : Long?, deathEnd : Long?, recovStart : Long?, recovEnd : Long?

}
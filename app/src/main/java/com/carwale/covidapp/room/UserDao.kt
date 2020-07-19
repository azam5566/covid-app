package com.carwale.covidapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carwale.covidapp.models.local.CountriesData
import com.carwale.covidapp.models.local.GlobalData

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGlobalData(globalData: GlobalData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountriesData(countryData : List<CountriesData>)

    @Query("SELECT SUM(totalConfirmed) FROM countries_data")
    fun getLiveDataTotalCases(): LiveData<Long>

    @Query("SELECT SUM(totalDeaths) FROM countries_data")
    fun getLiveDataTotalDeaths(): LiveData<Long>

    @Query("SELECT SUM(totalRecovered) FROM countries_data")
    fun getLiveDataTotalRecovered(): LiveData<Long>

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

    @Query("SELECT * FROM countries_data WHERE totalConfirmed between :casesStart and :casesEnd and totalDeaths between :deathStart and :deathEnd and totalRecovered between :recovStart and :recovEnd ORDER BY totalConfirmed DESC")
    fun getAllDataFiltered(casesStart : Long?, casesEnd : Long?, deathStart : Long?, deathEnd : Long?, recovStart : Long?, recovEnd : Long?): LiveData<List<CountriesData>>

}
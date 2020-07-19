package com.carwale.covidapp.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_data")
data class CountriesData(@PrimaryKey var Country : String ){
    constructor(
        Country : String,
        CountryCode : String?,
        Slug : String?,
        NewConfirmed : Long?,
        TotalConfirmed : Long?,
        NewDeaths : Long?,
        TotalDeaths : Long?,
        NewRecovered : Long?,
        TotalRecovered : Long?,
        Date : String?
    ): this(Country){
        this.countryCode = CountryCode
        this.slug = Slug
        this.newConfirmed = NewConfirmed
        this.totalConfirmed = TotalConfirmed
        this.newDeaths = NewDeaths
        this.totalDeaths = TotalDeaths
        this.newRecovered = NewRecovered
        this.totalRecovered = TotalRecovered
        this.date = Date
    }

    var countryCode : String? = null
    var slug : String? = null
    var newConfirmed : Long? = null
    var totalConfirmed : Long? = null
    var newDeaths : Long? = null
    var totalDeaths : Long? = null
    var newRecovered : Long? = null
    var totalRecovered : Long? = null
    var date : String? = null
}
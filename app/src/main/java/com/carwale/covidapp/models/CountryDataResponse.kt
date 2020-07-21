package com.carwale.covidapp.models

import com.carwale.covidapp.models.local.CountriesData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryDataResponse {

    @SerializedName("Country")
    @Expose
    val Country: String? = null

    @SerializedName("CountryCode")
    @Expose
    val CountryCode: String? = null

    @SerializedName("Slug")
    @Expose
    val Slug: String? = null

    @SerializedName("NewConfirmed")
    @Expose
    val NewConfirmed: Long? = null

    @SerializedName("TotalConfirmed")
    @Expose
    val TotalConfirmed: Long? = null

    @SerializedName("NewDeaths")
    @Expose
    val NewDeaths: Long? = null

    @SerializedName("TotalDeaths")
    @Expose
    val TotalDeaths: Long? = null

    @SerializedName("NewRecovered")
    @Expose
    val NewRecovered: Long? = null

    @SerializedName("TotalRecovered")
    @Expose
    val TotalRecovered: Long? = null

    @SerializedName("Date")
    @Expose
    val Date: String? = null

    fun toEntity() : CountriesData{
        return CountriesData(
            Country ?: "",
            CountryCode,
            Slug,
            NewConfirmed,
            TotalConfirmed,
            NewDeaths,
            TotalDeaths,
            NewRecovered,
            TotalRecovered,
            Date
        )
    }
}
package com.carwale.covidapp.models

import com.carwale.covidapp.models.local.GlobalData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GlobalDataResponse {
    @SerializedName("NewConfirmed")
    @Expose
    var newConfirmed: Long? = null

    @SerializedName("TotalConfirmed")
    @Expose
    var totalConfirmed: Long? = null

    @SerializedName("NewDeaths")
    @Expose
    var newDeaths: Long? = null

    @SerializedName("TotalDeaths")
    @Expose
    var totalDeaths: Long? = null

    @SerializedName("NewRecovered")
    @Expose
    var newRecovered: Long? = null

    @SerializedName("TotalRecovered")
    @Expose
    var totalRecovered: Long? = null

    fun toEntity() : GlobalData{
        return GlobalData(
            newConfirmed ?: 0,
            totalConfirmed,
            newDeaths,
            totalDeaths,
            newRecovered,
            totalRecovered
        )
    }

}
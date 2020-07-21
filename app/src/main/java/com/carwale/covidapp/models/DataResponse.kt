package com.carwale.covidapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse{

    @SerializedName("Global")
    @Expose
    val globalData : GlobalDataResponse? = null

    @SerializedName("Countries")
    @Expose
    val countriesData : List<CountryDataResponse>? = null

}
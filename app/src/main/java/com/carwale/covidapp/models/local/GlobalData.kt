package com.carwale.covidapp.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "global_data")
data class GlobalData(@PrimaryKey var newConfirmed: Long?){

    constructor(
        newConfirmed: Long,
        totalConfirmed: Long?,
        newDeaths: Long?,
        totalDeaths: Long?,
        newRecovered: Long?,
        totalRecovered: Long?
    ) : this(newConfirmed){
        this.totalConfirmed = totalConfirmed
        this.newDeaths = newDeaths
        this.totalDeaths = totalDeaths
        this.newRecovered = newRecovered
        this.totalRecovered = totalRecovered
        this.lastApiCalled = Calendar.getInstance().timeInMillis
    }

    var totalConfirmed: Long? = null
    var newDeaths: Long? = null
    var totalDeaths: Long? = null
    var newRecovered: Long? = null
    var totalRecovered: Long? = null
    var lastApiCalled: Long? = null
}
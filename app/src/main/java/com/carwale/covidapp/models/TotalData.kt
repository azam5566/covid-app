package com.carwale.covidapp.models

import androidx.room.Ignore

//class TotalData {
//    constructor(
//        sumCases : Long,
//        sumDeaths : Long,
//        sumRecov : Long
//    ){
//        this.sumTotalCases = sumCases
//        this.sumTotalDeaths = sumDeaths
//        this.sumTotalRecoverd = sumRecov
//    }
//
//    var sumTotalCases: Long? = null
//    var sumTotalRecoverd: Long? = null
//    var sumTotalDeaths: Long? = null
//}

data class TotalData(
    var sumCases : Long,
    var sumDeaths : Long,
    var sumRecov : Long
)
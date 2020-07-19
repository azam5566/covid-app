package com.carwale.covidapp.api

import com.carwale.covidapp.models.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("summary")
    fun getData() : Call<DataResponse>

}
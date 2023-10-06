package com.example.data.api

import com.example.domain.model.House
import com.example.domain.model.Street
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("get/allStreets/")
    suspend fun getAllStreets(): Response<List<Street>>

    @GET("get/houses/")
    suspend fun getStreetsHouses(@Query("street_id")id: String): Response<List<House>?>
}
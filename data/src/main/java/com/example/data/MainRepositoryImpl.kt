package com.example.data

import android.util.Log
import com.example.data.api.ApiService
import com.example.domain.model.House
import com.example.domain.model.Street
import com.example.domain.usecase.MainRepository
import org.json.JSONObject

class MainRepositoryImpl(private val apiService: ApiService): MainRepository {
    override suspend fun getAllStreets(): List<Street>? {
        return apiService.getAllStreets().body()
    }

    override suspend fun getStreetsHouses(streetId: String): List<House>? {
        return try {
            val result = apiService.getStreetsHouses(streetId).body()
//            Log.d("CHECK_API", "Returned list is $result")
            result
        }catch (e:Exception) {
            Log.d("Exception", "Getting data ERROR!")
            null
        }
    }
}
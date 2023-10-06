package com.example.domain.usecase

import com.example.domain.model.House
import com.example.domain.model.Street

interface MainRepository {

    suspend fun getAllStreets(): List<Street>?

    suspend fun getStreetsHouses(streetName: String): List<House>?
}
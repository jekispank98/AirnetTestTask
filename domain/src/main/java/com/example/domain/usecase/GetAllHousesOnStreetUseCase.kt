package com.example.domain.usecase

import com.example.domain.model.House

class GetAllHousesOnStreetUseCase(private val mainRepository: MainRepository) {

    suspend fun getAllHousesOnStreetUseCase(streetId: String): List<House>? {
        return mainRepository.getStreetsHouses(streetId)
    }
}
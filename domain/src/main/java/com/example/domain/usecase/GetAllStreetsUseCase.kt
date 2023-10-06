package com.example.domain.usecase

import com.example.domain.model.Street

class GetAllStreetsUseCase(private val mainRepository: MainRepository) {

    suspend fun getAllStreetsUseCase(): List<Street>? {
        return mainRepository.getAllStreets()
    }
}
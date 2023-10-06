package com.example.domain.di
import com.example.domain.usecase.GetAllHousesOnStreetUseCase
import com.example.domain.usecase.GetAllStreetsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllStreetsUseCase> {
        GetAllStreetsUseCase(mainRepository = get())
    }

    factory<GetAllHousesOnStreetUseCase> {
        GetAllHousesOnStreetUseCase(mainRepository = get())
    }
}
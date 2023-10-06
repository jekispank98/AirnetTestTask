package com.example.airnettesttask.di
import com.example.airnettesttask.MainViewModel
import org.koin.dsl.module

val uiModule = module{

    single<MainViewModel> {
        MainViewModel(
            getAllStreetsUseCase = get(),
            getAllHousesOnStreetUseCase = get(),
            application = get()
        )
    }
}
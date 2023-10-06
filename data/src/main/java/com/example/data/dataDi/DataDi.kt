package com.example.data.dataDi

import com.example.data.MainRepositoryImpl
import com.example.data.api.ApiService
import com.example.domain.usecase.MainRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://stat-api.airnet.ru/v2/utils/"

val dataModule = module {
    single<ApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
    single<MainRepository> {
        MainRepositoryImpl(apiService = get())
    }
}
package com.example.airnettesttask

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.House
import com.example.domain.model.Street
import com.example.domain.usecase.GetAllHousesOnStreetUseCase
import com.example.domain.usecase.GetAllStreetsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllStreetsUseCase: GetAllStreetsUseCase,
    private val getAllHousesOnStreetUseCase: GetAllHousesOnStreetUseCase,
    private val application: Application
) : ViewModel() {

    private var _streetsData = MutableLiveData<List<Street>?>()
    val streetsData: LiveData<List<Street>?> = _streetsData

    private var _housesData = MutableLiveData<List<House>?>()
    val housesData: LiveData<List<House>?> = _housesData

    fun getAllStreets() {
        CoroutineScope(Dispatchers.Main).launch {
            val streetResult = getAllStreetsUseCase.getAllStreetsUseCase()
            _streetsData.value = streetResult
        }
    }

    fun getAllHousesOnStreet(streetId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val housesResult = getAllHousesOnStreetUseCase.getAllHousesOnStreetUseCase(streetId)
            _housesData.value = housesResult
        }
    }

    fun getStreetId(street: String?): String? {
        val streetItem = streetsData.value?.find { it.street == street }
        return streetItem?.street_id
    }
    fun getHouseId(house:String?): String? {
        val housesItem = housesData.value?.find { it.house == house }
        return housesItem?.house_id
    }
}
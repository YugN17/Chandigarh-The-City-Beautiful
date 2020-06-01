package com.chandigarhthecitybeautiful.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chandigarhthecitybeautiful.model.Place

interface IPlacesDataRepository {
    val allPlaces : LiveData<List<Place>>

    @ExperimentalStdlibApi
    suspend fun updateDB()
    fun getPlace(id: Int): Place
    val message: MutableLiveData<String>
}
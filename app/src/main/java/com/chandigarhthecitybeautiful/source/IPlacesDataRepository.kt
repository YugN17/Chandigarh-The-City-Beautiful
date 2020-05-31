package com.chandigarhthecitybeautiful.source

import androidx.lifecycle.LiveData
import com.chandigarhthecitybeautiful.model.Place

interface IPlacesDataRepository {
    val allPlaces : LiveData<List<Place>>

    @ExperimentalStdlibApi
    suspend fun updateDB()
    fun getPlace(id: Int): Place
}
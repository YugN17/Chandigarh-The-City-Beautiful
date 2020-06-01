package com.chandigarhthecitybeautiful.source.local

import androidx.lifecycle.LiveData
import com.chandigarhthecitybeautiful.model.Place

interface ILocalDataSource {

    /* function syncs offline DB with the received response
        * @param data: response from the network API
        */
    suspend fun syncDB(places: List<Place>): Boolean

    fun getPlaces(): LiveData<List<Place>>

    fun getPlace(id: Int): Place
}
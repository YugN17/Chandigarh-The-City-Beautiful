package com.chandigarhthecitybeautiful.source.local

import androidx.lifecycle.LiveData
import com.chandigarhthecitybeautiful.model.Place

interface ILocalDataSource {
    val allFacts: LiveData<List<Place>>

    /* function syncs offline DB with the received response
        * @param data: response from the network API
        */
    @ExperimentalStdlibApi
    suspend fun syncDB(places: List<Place>): Boolean

    fun getPlace(id:Int):Place
}
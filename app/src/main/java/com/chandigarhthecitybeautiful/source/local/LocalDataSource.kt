package com.chandigarhthecitybeautiful.source.local

import androidx.lifecycle.LiveData
import com.chandigarhthecitybeautiful.model.Place
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val placeDAO: PlaceDAO,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ILocalDataSource {
    override val allFacts = placeDAO.allPlaces()


    /* function syncs offline DB with the received response
     * @param data: response from the network API
     */
    @ExperimentalStdlibApi
    override suspend fun syncDB(places: List<Place>): Boolean = withContext(ioDispatcher) {
        for (place in places) {
            placeDAO.insertPlace(place)
        }
        return@withContext true
    }

    override fun getPlace(id: Int): Place {
        return placeDAO.getPlace(id)
    }
}
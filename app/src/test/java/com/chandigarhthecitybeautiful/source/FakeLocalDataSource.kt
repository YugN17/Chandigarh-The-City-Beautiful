package com.chandigarhthecitybeautiful.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.local.ILocalDataSource


class FakeLocalDataSource(
    var allPlaces: MutableLiveData<List<Place>>
) : ILocalDataSource {

    @ExperimentalStdlibApi
    override suspend fun syncDB(places: List<Place>): Boolean {
        allPlaces.postValue(places)
        return true
    }

    override fun getPlaces(): LiveData<List<Place>> {
        return allPlaces
    }

    override fun getPlace(id: Int): Place {
        val invalidPlace = Place(-1, "Invalid Name", "", "", "", "")
        for (place in allPlaces.value!!)
            if (place.id == id)
                return place
        return invalidPlace
    }
}
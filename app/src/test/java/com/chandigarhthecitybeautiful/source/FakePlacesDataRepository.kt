package com.chandigarhthecitybeautiful.source

import androidx.lifecycle.MutableLiveData
import com.chandigarhthecitybeautiful.model.Place

class FakePlacesDataRepository(
    override val allPlaces: MutableLiveData<List<Place>>,
    override val message: MutableLiveData<String>
) : IPlacesDataRepository {

    var responseFromServer: ArrayList<Place> = ArrayList()

    override suspend fun updateDB() {
        allPlaces.postValue(responseFromServer)
    }

    override fun getPlace(id: Int): Place {
        val invalidPlace = Place(-1, "Invalid Name", "", "", "", "")
        for (place in allPlaces.value!!)
            if (place.id == id)
                return place
        return invalidPlace
    }
}
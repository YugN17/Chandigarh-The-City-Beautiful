package com.chandigarhthecitybeautiful.source

import androidx.lifecycle.MutableLiveData
import com.chandigarhthecitybeautiful.model.Place

class FakeAndroidPlacesDataRepository(
) : IPlacesDataRepository {

    override val allPlaces: MutableLiveData<List<Place>> = MutableLiveData()
    override val message: MutableLiveData<String> = MutableLiveData()


    override suspend fun updateDB() {

    }

    override fun getPlace(id: Int): Place {
        val invalidPlace = Place(-1, "Invalid Name", "", "", "", "")
        for (place in allPlaces.value!!)
            if (place.id == id)
                return place
        return invalidPlace
    }

    fun setupData(places: List<Place>, msg: String) {
        allPlaces.postValue(places)
        message.postValue(msg)
    }
}
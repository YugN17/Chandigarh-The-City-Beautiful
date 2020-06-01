package com.chandigarhthecitybeautiful.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.IPlacesDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceDetailsViewModel(private val placesDataRepository: IPlacesDataRepository) : ViewModel() {

    var place: MutableLiveData<Place> = MutableLiveData()

    fun getPlaceData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            place.postValue(placesDataRepository.getPlace(id))
        }
    }

    @Suppress("UNCHECKED_CAST")
    class PlaceDetailsViewModelFactory(
        private val placesDataRepository: IPlacesDataRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (PlaceDetailsViewModel(
                placesDataRepository
            ) as T)
    }
}


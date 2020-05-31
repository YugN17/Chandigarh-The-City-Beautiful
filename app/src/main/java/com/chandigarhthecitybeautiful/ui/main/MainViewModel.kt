package com.chandigarhthecitybeautiful.ui.main

import androidx.lifecycle.*
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.IPlacesDataRepository
import com.chandigarhthecitybeautiful.source.PlacesDataRepository
import kotlinx.coroutines.launch

class MainViewModel(private val placesDataRepository: PlacesDataRepository): ViewModel() {

    val allPlaces:LiveData<List<Place>> = placesDataRepository.allPlaces

    @ExperimentalStdlibApi
    fun syncDataFromServer(){
        viewModelScope.launch {
            placesDataRepository.updateDB()
        }
    }

    @Suppress("UNCHECKED_CAST")
    class PlacesViewModelFactory(
        private val placesDataRepository: IPlacesDataRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (MainViewModel(placesDataRepository as PlacesDataRepository) as T)
    }
}
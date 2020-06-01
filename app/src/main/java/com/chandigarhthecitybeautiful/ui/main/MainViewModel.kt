package com.chandigarhthecitybeautiful.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.IPlacesDataRepository
import com.chandigarhthecitybeautiful.source.PlacesDataRepository
import kotlinx.coroutines.launch

class MainViewModel(private val placesDataRepository: PlacesDataRepository): ViewModel() {

    val allPlaces: LiveData<List<Place>> = placesDataRepository.allPlaces
    val msg: LiveData<String> = placesDataRepository.message

    @ExperimentalStdlibApi
    fun syncDataFromServer() {
        viewModelScope.launch {
            placesDataRepository.updateDB()
        }
    }

    fun setMessage(msg: String) {
        placesDataRepository.message.postValue(msg)
    }

    @Suppress("UNCHECKED_CAST")
    class PlacesViewModelFactory(
        private val placesDataRepository: IPlacesDataRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (MainViewModel(placesDataRepository as PlacesDataRepository) as T)
    }
}
package com.chandigarhthecitybeautiful

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.IPlacesDataRepository
import com.chandigarhthecitybeautiful.source.PlacesDataRepository
import com.chandigarhthecitybeautiful.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceDetailsViewModel(private val placesDataRepository: PlacesDataRepository) : ViewModel() {

    var place: MutableLiveData<Place> = MutableLiveData()

    fun getPlaceData(id:Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            place.postValue(placesDataRepository.getPlace(id))
        }
    }

    @Suppress("UNCHECKED_CAST")
    class PlaceDetailsViewModelFactory(
        private val placesDataRepository: IPlacesDataRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (PlaceDetailsViewModel(placesDataRepository as PlacesDataRepository) as T)
    }
}


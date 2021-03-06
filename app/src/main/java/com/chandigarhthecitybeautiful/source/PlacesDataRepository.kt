package com.chandigarhthecitybeautiful.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.local.ILocalDataSource
import com.chandigarhthecitybeautiful.source.remote.IRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesDataRepository(private val localDataSource: ILocalDataSource,
                           private val remoteDataSource: IRemoteDataSource,
                           private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO ) :
    IPlacesDataRepository {

    override val allPlaces: LiveData<List<Place>> = localDataSource.getPlaces()
    override val message: MutableLiveData<String> = MutableLiveData("")

    override suspend fun updateDB() {
        withContext(ioDispatcher) {
            when (val result = remoteDataSource.getPlacesFromServer()) {
                is Result.Success -> localDataSource.syncDB(result.data)
                is Result.Error -> message.postValue(result.exception.message)
                else -> message.postValue("")
            }

        }
    }

    override fun getPlace(id: Int): Place {
        return localDataSource.getPlace(id)
    }
}
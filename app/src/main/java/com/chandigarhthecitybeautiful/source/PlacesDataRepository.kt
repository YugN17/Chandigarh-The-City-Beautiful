package com.chandigarhthecitybeautiful.source

import android.util.Log
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

    override val allPlaces : LiveData<List<Place>> = localDataSource.allFacts


    @ExperimentalStdlibApi
    override suspend fun updateDB() {
        withContext(ioDispatcher) {
            val result = remoteDataSource.getPlacesFromServer()
            Log.d("tag",result.toString())
            if (result is Result.Success)
                localDataSource.syncDB(result.data)
        }
    }

    override fun getPlace(id: Int): Place {
        return localDataSource.getPlace(id)
    }
}
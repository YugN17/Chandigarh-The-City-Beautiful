package com.chandigarhthecitybeautiful.source.remote

import android.content.Context
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RemoteDataSource() : IRemoteDataSource {

    override suspend fun getPlacesFromServer(): Result<List<Place>> {
        var result: Result<List<Place>>
        val getPlacesDeferred = PlaceAPI.retrofitService.getPlacesAsync()
            result = try{
                Result.Success(getPlacesDeferred.await())
            } catch (exception:Exception) {
                Result.Error(exception)
            }
        return result
    }

}
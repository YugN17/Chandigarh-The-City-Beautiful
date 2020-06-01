package com.chandigarhthecitybeautiful.source.remote

import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.Result

class RemoteDataSource() : IRemoteDataSource {

    override suspend fun getPlacesFromServer(): Result<List<Place>> {
        val result: Result<List<Place>>
        val getPlacesDeferred = PlaceAPI.retrofitService.getPlacesAsync()
            result = try{
                Result.Success(getPlacesDeferred.await())
            } catch (exception:Exception) {
                Result.Error(exception)
            }
        return result
    }

}
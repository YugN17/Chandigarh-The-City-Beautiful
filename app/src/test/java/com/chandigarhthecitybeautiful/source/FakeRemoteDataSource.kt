package com.chandigarhthecitybeautiful.source

import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.remote.IRemoteDataSource

class FakeRemoteDataSource(
    var isConnected: Boolean,
    var response: List<Place>
) : IRemoteDataSource {
    override suspend fun getPlacesFromServer(): Result<List<Place>> {
        return if (isConnected)
            Result.Success(response)
        else
            Result.Error(Exception("No Internet Connection"))
    }

}
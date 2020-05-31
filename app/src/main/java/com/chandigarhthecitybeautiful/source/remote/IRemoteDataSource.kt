package com.chandigarhthecitybeautiful.source.remote

import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.Result


interface IRemoteDataSource {
    /* function to create new network API request
      */
    suspend fun getPlacesFromServer(): Result<List<Place>>
}
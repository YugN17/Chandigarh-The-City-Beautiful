package com.chandigarhthecitybeautiful

import android.app.Application
import com.chandigarhthecitybeautiful.source.IPlacesDataRepository

class MyApplication : Application() {

    val placesDataRepository: IPlacesDataRepository
        get() = ServiceLocator.providePlacesRepository(this)
}
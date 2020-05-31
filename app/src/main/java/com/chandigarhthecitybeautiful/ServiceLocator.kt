package com.chandigarhthecitybeautiful

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.chandigarhthecitybeautiful.source.IPlacesDataRepository
import com.chandigarhthecitybeautiful.source.PlacesDataRepository
import com.chandigarhthecitybeautiful.source.local.LocalDataSource
import com.chandigarhthecitybeautiful.source.local.PlacesDataBase
import com.chandigarhthecitybeautiful.source.remote.RemoteDataSource

object ServiceLocator {
    private var database: PlacesDataBase? = null

    @Volatile
    var iPlacesDataRepository: IPlacesDataRepository? = null
        @VisibleForTesting set

    private val lock = Any()


    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            iPlacesDataRepository = null
        }
    }

    fun providePlacesRepository(context: Context): IPlacesDataRepository {
        synchronized(this) {
            return iPlacesDataRepository ?: createPlacesRepository(context)
        }
    }

    private fun createPlacesRepository(context: Context): PlacesDataRepository{
        val newRepo = PlacesDataRepository(createLocalDataSource(context), RemoteDataSource())
        iPlacesDataRepository = newRepo
        return newRepo
    }


    private fun createLocalDataSource(context: Context): LocalDataSource {
        val database = database ?: createDataBase(context)
        return LocalDataSource(database.placeDAO())
    }

    private fun createDataBase(context: Context): PlacesDataBase {
        val result = Room.databaseBuilder(
            context.applicationContext, PlacesDataBase::class.java, context.getString(
                R.string.repository
            )
        ).build()
        database = result
        return result
    }
}
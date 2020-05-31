package com.chandigarhthecitybeautiful.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chandigarhthecitybeautiful.R
import com.chandigarhthecitybeautiful.model.Place

@Database(entities = [Place::class], version = 1)
abstract class PlacesDataBase : RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO

    companion object {
        @Volatile
        private var placesDatabaseInstance: PlacesDataBase? = null

        internal fun getDatabase(context: Context): PlacesDataBase? {
            if (placesDatabaseInstance == null) {
                synchronized(PlacesDataBase::class.java) {
                    if (placesDatabaseInstance == null) {
                        placesDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            PlacesDataBase::class.java,
                            context.getString(
                                R.string.repository
                            )
                        )
                            .build()
                    }
                }
            }
            return placesDatabaseInstance
        }
    }
}
package com.chandigarhthecitybeautiful.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chandigarhthecitybeautiful.model.Place

@Database(entities = [Place::class], version = 1)
abstract class PlacesDataBase : RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO
}
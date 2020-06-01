package com.chandigarhthecitybeautiful.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chandigarhthecitybeautiful.model.Place

@Dao
interface PlaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: Place)

    @Query("select * from places ORDER BY id ")
    fun allPlaces(): LiveData<List<Place>>

    @Query("select * from places where id=:id ")
    fun getPlace(id:Int): Place

}
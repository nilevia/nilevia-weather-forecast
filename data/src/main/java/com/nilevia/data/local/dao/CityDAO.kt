package com.nilevia.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nilevia.data.models.CityDTO

@Dao
interface CityDAO {
    @Insert(onConflict = REPLACE)
    fun insertCity(exchange: CityDTO)

    @Query("SELECT * FROM city")
    fun getCity(): List<CityDTO>?

    @Query("Delete FROM city where city.id = :id")
    fun deleteCity(id: String)
}

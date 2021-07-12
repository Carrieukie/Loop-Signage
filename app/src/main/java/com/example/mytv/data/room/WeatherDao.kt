package com.example.mytv.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytv.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Query("SELECT * FROM weather")
    fun getWeather() : Flow<Weather>

    @Query("DELETE FROM weather")
    fun deleteAllWeather()


}
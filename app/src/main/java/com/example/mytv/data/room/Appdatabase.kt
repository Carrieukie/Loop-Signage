package com.example.mytv.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mytv.data.room.converters.*
import com.example.mytv.model.Weather

@Database(entities = [Weather::class], version = 1, exportSchema = false)
@TypeConverters(
    CityConverter::class,
    CloudConverter::class,
    CoordConverter::class,
    ListItemConverter::class,
    MainConverter::class,
    RainConverter::class,
    SysConverter::class,
    WeatherItemConverter::class,
    WindConverter::class
)
abstract class Appdatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}
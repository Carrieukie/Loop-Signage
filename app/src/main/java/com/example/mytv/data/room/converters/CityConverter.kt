package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CityConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCity(city: City?): String? {
        val type = object : TypeToken<City?>() {}.type
        return gson.toJson(city, type)
    }

    @TypeConverter
    fun toCity(city: String?) : City {
        if (city.isNullOrEmpty()) return City()

        val type = object : TypeToken<City>() {}.type
        return gson.fromJson(city, type)
    }

}
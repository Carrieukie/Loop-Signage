package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.WeatherItem
import com.example.mytv.model.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherItemConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromWeatherItem(weatherItem: List<WeatherItem?>?): String? {
        val type = object : TypeToken<List<WeatherItem?>?>() {}.type
        return gson.toJson(weatherItem, type)
    }

    @TypeConverter
    fun toWeatherItem(weatherItem: String?) : List<WeatherItem?>? {
        if (weatherItem.isNullOrEmpty()) return listOf()

        val type = object : TypeToken<List<WeatherItem?>?>() {}.type
        return gson.fromJson(weatherItem, type)
    }

}
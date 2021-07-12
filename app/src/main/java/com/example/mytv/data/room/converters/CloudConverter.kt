package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.Clouds
import com.example.mytv.model.Coord
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CloudConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromClouds(clouds: Clouds?): String? {
        val type = object : TypeToken<Clouds?>() {}.type
        return gson.toJson(clouds, type)
    }

    @TypeConverter
    fun toClouds(clouds: String?) : Clouds {
        if (clouds.isNullOrEmpty()) return Clouds()

        val type = object : TypeToken<Clouds>() {}.type
        return gson.fromJson(clouds, type)
    }
}
package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.Main
import com.example.mytv.model.Sys
import com.example.mytv.model.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WindConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWind(wind: Wind?): String? {
        val type = object : TypeToken<Wind?>() {}.type
        return gson.toJson(wind, type)
    }

    @TypeConverter
    fun toWind(wind: String?) : Wind {
        if (wind.isNullOrEmpty()) return Wind()

        val type = object : TypeToken<Wind>() {}.type
        return gson.fromJson(wind, type)
    }
}
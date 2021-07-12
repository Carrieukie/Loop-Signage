package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.Rain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RainConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromRain(rain: Rain?): String? {
        val type = object : TypeToken<Rain?>() {}.type
        return gson.toJson(rain, type)
    }

    @TypeConverter
    fun toRain(rain: String?) : Rain {
        if (rain.isNullOrEmpty()) return Rain()

        val type = object : TypeToken<Rain>() {}.type
        return gson.fromJson(rain, type)
    }

}
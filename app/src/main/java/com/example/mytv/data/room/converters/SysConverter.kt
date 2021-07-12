package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.Main
import com.example.mytv.model.Sys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SysConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSys(sys: Sys?): String? {
        val type = object : TypeToken<Sys?>() {}.type
        return gson.toJson(sys, type)
    }

    @TypeConverter
    fun toSys(sys: String?) : Sys {
        if (sys.isNullOrEmpty()) return Sys()

        val type = object : TypeToken<Sys>() {}.type
        return gson.fromJson(sys, type)
    }
}
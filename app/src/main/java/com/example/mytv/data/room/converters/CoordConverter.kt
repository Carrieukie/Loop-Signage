package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.Coord
import com.example.mytv.model.Main
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CoordConverter {


    private val gson = Gson()

    @TypeConverter
    fun fromCoord(coord: Coord?): String? {
        val type = object : TypeToken<Coord?>() {}.type
        return gson.toJson(coord, type)
    }

    @TypeConverter
    fun toCoord(coord: String?) : Coord {
        if (coord.isNullOrEmpty()) return Coord()

        val type = object : TypeToken<Coord>() {}.type
        return gson.fromJson(coord, type)
    }

}
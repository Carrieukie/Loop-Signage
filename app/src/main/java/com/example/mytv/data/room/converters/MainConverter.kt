package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.Main
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromMain(main: Main?): String? {
        val type = object : TypeToken<Main?>() {}.type
        return gson.toJson(main, type)
    }

    @TypeConverter
    fun toMain(main: String?) : Main {
        if (main.isNullOrEmpty()) return Main()

        val type = object : TypeToken<Main>() {}.type
        return gson.fromJson(main, type)
    }


}
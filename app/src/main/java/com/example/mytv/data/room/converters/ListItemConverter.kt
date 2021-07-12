package com.example.mytv.data.room.converters

import androidx.room.TypeConverter
import com.example.mytv.model.City
import com.example.mytv.model.ListItem
import com.example.mytv.model.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListItemConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromListItem(listItem: List<ListItem?>?): String? {
        val type = object : TypeToken<List<ListItem?>?>() {}.type
        return gson.toJson(listItem, type)
    }

    @TypeConverter
    fun toListItem(listItem: String?) : List<ListItem?>? {
        if (listItem.isNullOrEmpty()) return listOf()

        val type = object : TypeToken<List<ListItem?>?>() {}.type
        return gson.fromJson(listItem, type)
    }

}
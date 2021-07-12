package com.example.mytv.data.network

import com.example.mytv.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast?")
    suspend fun getWeather(
        @Query("appid") appid : String,
        @Query("q") location : String
    ) : Weather

}
//            "?appid=2c826d8cf841a336727a7adde4221aca&q=Nyahururu" +
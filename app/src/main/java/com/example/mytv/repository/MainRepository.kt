package com.example.mytv.repository

import androidx.room.withTransaction
import com.example.mytv.data.network.ApiService
import com.example.mytv.data.room.Appdatabase
import com.example.mytv.utils.Constants
import com.example.mytv.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val database: Appdatabase,
    private val apiService : ApiService,
) {

    private val weatherDao = database.weatherDao()

    fun getWeather(location : String) = networkBoundResource(
        query = {
            weatherDao.getWeather()
        },
        fetch = {
            delay(2000)
            apiService.getWeather(
                Constants.APP_ID,
                location
            )
        },
        saveFetchResult = { weather ->
            database.withTransaction {
                weatherDao.deleteAllWeather()
                weatherDao.insert(weather)
            }
        }
    )


}
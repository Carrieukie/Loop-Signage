package com.example.mytv.di

import android.app.Application
import androidx.room.Room
import com.example.mytv.data.room.Appdatabase
import com.example.mytv.data.room.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Application) : Appdatabase{
        return Room.databaseBuilder(context,Appdatabase::class.java,"weather_db").build()
    }

    @Provides
    @Singleton
    fun providesWeatherDao(appdatabase: Appdatabase) : WeatherDao{
        return appdatabase.weatherDao()
    }

}
package com.example.mytv.di

import android.content.SharedPreferences
import com.example.mytv.data.network.ApiService
import com.example.mytv.data.room.Appdatabase
import com.example.mytv.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(appdatabase: Appdatabase , apiService: ApiService ) : MainRepository{
        return MainRepository(appdatabase,apiService)
    }

}
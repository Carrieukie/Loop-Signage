package com.example.mytv.ui.fragments.watch

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mytv.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentWatchViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var currentWindow : Int  = 0
    var playbackPosition : Long  = 0

    fun weather(location: String) = mainRepository.getWeather(location).asLiveData()

}
package br.com.euvickson.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.euvickson.jetweatherforecast.data.DataOrException
import br.com.euvickson.jetweatherforecast.model.WeatherObject
import br.com.euvickson.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    private val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>> = mutableStateOf(DataOrException(null, true, Exception()))

    init {
        loadWeather()
    }

    private fun loadWeather(){
        getWeather("Seattle")
    }

    private fun getWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        Log.d("GET", "getWeather: ${data.value.data.toString()}")
    }

}
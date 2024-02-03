package br.com.euvickson.jetweatherforecast.repository

import android.util.Log
import br.com.euvickson.jetweatherforecast.data.DataOrException
import br.com.euvickson.jetweatherforecast.model.Weather
import br.com.euvickson.jetweatherforecast.model.WeatherObject
import br.com.euvickson.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(
        cityQuerry: String,
        units: String = "metric"
    ): DataOrException<Weather, Boolean, Exception> {

        val response = try {
            api.getWeather(query = cityQuerry)
        } catch (e: Exception) {
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)

    }
}
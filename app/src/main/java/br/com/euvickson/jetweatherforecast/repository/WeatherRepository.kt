package br.com.euvickson.jetweatherforecast.repository

import br.com.euvickson.jetweatherforecast.data.DataOrException
import br.com.euvickson.jetweatherforecast.model.WeatherObject
import br.com.euvickson.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(
        cityQuerry: String,
        units: String = ""
    ): DataOrException<WeatherObject, Boolean, Exception> {

        val response = try {
            api.getWeather(query = cityQuerry)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)

    }
}
package br.com.euvickson.jetweatherforecast.network

import br.com.euvickson.jetweatherforecast.model.Weather
import br.com.euvickson.jetweatherforecast.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = API_KEY
    ): Weather

}
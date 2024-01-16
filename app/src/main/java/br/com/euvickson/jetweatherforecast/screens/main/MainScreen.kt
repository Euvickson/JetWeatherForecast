package br.com.euvickson.jetweatherforecast.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.euvickson.jetweatherforecast.data.DataOrException
import br.com.euvickson.jetweatherforecast.model.Weather
import br.com.euvickson.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "Seattle")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null){
        MainScraffold(weather = weatherData.data!!, navController = navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScraffold(weather: Weather, navController: NavController) {

    Scaffold (
        topBar = {
            WeatherAppBar(title = "Helena, MT")
        }
    ) {
        Column (modifier = Modifier.padding(it)){
            MainContent(data = weather)
        }
    }
    

}

@Composable
fun MainContent(data: Weather) {
    Text(text = data.city.name)
}

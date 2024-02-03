package br.com.euvickson.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.euvickson.jetweatherforecast.R
import br.com.euvickson.jetweatherforecast.data.DataOrException
import br.com.euvickson.jetweatherforecast.model.Weather
import br.com.euvickson.jetweatherforecast.model.WeatherItem
import br.com.euvickson.jetweatherforecast.utils.formatDate
import br.com.euvickson.jetweatherforecast.utils.formatDateTime
import br.com.euvickson.jetweatherforecast.utils.formatDecimals
import br.com.euvickson.jetweatherforecast.widgets.WeatherAppBar
import coil.compose.AsyncImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "sergipe")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                elevation = 5.dp,
            ) {
                Log.d("TAG", "MainScraffold: Button Clicked")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainContent(data = weather)
        }
    }


}

@Composable
fun MainContent(data: Weather) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Text(
                text = formatDate(data.list[0].dt),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp)
            )

            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .size(200.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //https://openweathermap.org/img/wn/01d.png
                    WeatherStateImage(imageUrl = imageUrl)

                    Text(text = formatDecimals(data.list[0].temp.day) + "º", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
                    Text(text = data.list[0].weather[0].main, fontStyle = FontStyle.Italic, fontSize = 20.sp)

                }

            }
        HumidityWindPressureRow(data.list[0])
        Divider()
        SunsetSunRiseRow(data.list[0])
        Text(text = "This Week", fontWeight = FontWeight.Bold)

        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), shape = RoundedCornerShape(14.dp)) {
            WeekRow(data)
        }

    }
}

@Composable
fun WeekRow(data: Weather) {
    LazyColumn(
        modifier = Modifier.padding(2.dp),
        contentPadding = PaddingValues(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        items(data.list) {
            DayInfo(weather = it)
        }
    }
}

@Composable
fun DayInfo(weather: WeatherItem) {

    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    ElevatedCard (modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .clip(
            shape = CircleShape.copy(topEnd = CornerSize(6.dp))
        ),
        elevation = CardDefaults.cardElevation( defaultElevation = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)){

        Row (modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

            Text(text = formatDate(timestamp = weather.dt).split(",")[0], fontSize = 18.sp)

            WeatherStateImage(imageUrl = imageUrl, size = 60)

            Text(text = weather.weather[0].description,
                Modifier
                    .clip(CircleShape)
                    .background(color = Color(0xFFFFC400))
                    .padding(4.dp),
                fontSize = 12.sp,
                color = Color.Black
            )
            
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
                )) {
                    append(formatDecimals(weather.temp.max) + "º")
                }

                withStyle(
                    SpanStyle(
                        color = Color.LightGray
                    )) {
                    append(formatDecimals(weather.temp.min) + "º")
                }
            })
        }

    }
}

@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "Sunrise Icon", modifier = Modifier.size(30.dp))
            Text(text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.bodyMedium)
        }

        Row (modifier = Modifier.padding(4.dp)){
            Text(text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.bodyMedium)
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "Sunset Icon", modifier = Modifier.size(30.dp))
        }

    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row (modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "Humidity Icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.humidity}%",
                style = MaterialTheme.typography.bodyMedium)
        }

        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "Pressure Icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.pressure} psi",
                style = MaterialTheme.typography.bodyMedium)
        }

        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "Wind Icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.gust}mph",
                style = MaterialTheme.typography.bodyMedium)
        }

    }
}

@Composable
fun WeatherStateImage(imageUrl: String, size: Int = 80) {
    AsyncImage(model = imageUrl, contentDescription = "Icon Image", modifier = Modifier.size(size.dp))
}

package br.com.euvickson.jetweatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.euvickson.jetweatherforecast.R
import br.com.euvickson.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
                 WeatherAppBar(
                     title = "About",
                     icon = Icons.Default.ArrowBack,
                     isMainScreen = false,
                     initialPadding = 50.dp,
                     navController = navController
                 ) {
                     navController.popBackStack()
                 }
        },
    ) {
        Surface (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column (modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.about_app), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.api_used), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Light)

            }
        }

    }
}
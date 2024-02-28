package br.com.euvickson.jetweatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.euvickson.jetweatherforecast.model.Unit
import br.com.euvickson.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, settingsViewModel: SettingsViewModel = hiltViewModel()) {

    Scaffold (topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            initialPadding = 100.dp
        )
    }){
        Surface (modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                var unitToggleState by remember { mutableStateOf(false) }
                val measurementUnits = listOf("Imperial (F)", "Metric (C)")
                var choiceState by remember { mutableStateOf("") }

                Text(
                    text = "Change Units Of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        choiceState = if (unitToggleState) {
                            "Imperial (F)"
                        } else {
                            "Metric (C)"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC")
                }

                Button(
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Unit(unit = choiceState ))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC400)
                    )
                ) {
                    Text(text = "Save", modifier = Modifier.padding(4.dp), fontSize = 17.sp, color = Color.Black)
                }

            }
        }
    }
}
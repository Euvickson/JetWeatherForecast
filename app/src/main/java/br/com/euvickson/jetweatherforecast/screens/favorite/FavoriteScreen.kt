package br.com.euvickson.jetweatherforecast.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.euvickson.jetweatherforecast.model.Favorite
import br.com.euvickson.jetweatherforecast.navigation.WeatherScreens
import br.com.euvickson.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Favorite Cities",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                initialPadding = 50.dp
            ) { navController.popBackStack() }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoriteViewModel.favList.collectAsState().value

                LazyColumn {

                    items(list) { favorite ->
                        CityRow(
                            favorite = favorite,
                            navController = navController,
                            favoriteViewModel
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel
) {

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                
                       navController.navigate(WeatherScreens.MainScreen.name +"/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFFFC400)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp))

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable {
                    favoriteViewModel.deleteFavorite(favorite)
                },
                tint = Color.Red.copy(alpha = 0.3f)
            )

        }

    }

}

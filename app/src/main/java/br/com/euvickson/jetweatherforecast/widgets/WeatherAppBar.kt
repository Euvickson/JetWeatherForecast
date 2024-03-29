package br.com.euvickson.jetweatherforecast.widgets

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.euvickson.jetweatherforecast.model.Favorite
import br.com.euvickson.jetweatherforecast.navigation.WeatherScreens
import br.com.euvickson.jetweatherforecast.screens.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    initialPadding: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val showDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)

    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.tertiary,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 15.sp,
                modifier = Modifier.padding(start = initialPadding)
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }

                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { onButtonClicked.invoke() }
                )
            }
            if (isMainScreen) {

                val isAlredyFavList = favoriteViewModel.favList.collectAsState().value.filter { item ->
                    (item.city == title.split(",")[0])
                }

                Icon(
                    imageVector = if (isAlredyFavList.isEmpty()) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = "FavoriteIcon",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .scale(0.9f)
                        .clickable {
                            val dataList = title.split(",")
                            val favorite = Favorite(city = dataList[0], country = dataList[1])

                            if (isAlredyFavList.isEmpty()) {
                                favoriteViewModel.insertFavorite(favorite)
                                Toast.makeText(context, "Added to the Favorites", Toast.LENGTH_SHORT).show()
                            } else {
                                favoriteViewModel.deleteFavorite(favorite)
                                Toast.makeText(context, "Removed from the Favorites", Toast.LENGTH_SHORT).show()
                            }

                        },
                    tint = Color.Red.copy(alpha = 0.6f)
                )

            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.shadow(elevation = elevation)
    )

}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember { mutableStateOf(true) }
    val items = listOf("About", "Favorite", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            },
            modifier = Modifier.width(140.dp)
        ) {

            items.forEachIndexed { _, text ->

                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorite" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                    },
                    text = {
                        Text(text = text,
                            fontWeight = FontWeight.W300,
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    route = when (text) {
                                        "About" -> WeatherScreens.AboutScreen.name
                                        "Favorite" -> WeatherScreens.FavoriteScreen.name
                                        else -> WeatherScreens.SettingsScreen.name
                                    }
                                )
                            })
                    })

            }
        }
    }
}

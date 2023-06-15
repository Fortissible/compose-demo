package com.example.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composedemo.data.Resource
import com.example.composedemo.di.ViewModelFactory
import com.example.composedemo.screen.about.AboutScreen
import com.example.composedemo.screen.mangadetail.DetailViewModel
import com.example.composedemo.screen.mangadetail.MangaDetailScreen
import com.example.composedemo.screen.mangalist.ListViewModel
import com.example.composedemo.screen.mangalist.MangaListScreen
import com.example.composedemo.ui.components.AnimeItem
import com.example.composedemo.ui.route.BottomNavItems
import com.example.composedemo.ui.route.Screen
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory : ViewModelFactory = ViewModelFactory.getInstance(this)
        val listViewModel : ListViewModel by viewModels{
            viewModelFactory
        }
        val detailViewModel : DetailViewModel by viewModels{
            viewModelFactory
        }
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SettingUpTheScreen(
                        listViewModel,
                        detailViewModel,
                        navHostController = rememberNavController()
                    )
                }
            }
        }
    }
}

@Composable
fun SettingUpTheScreen(
    listViewModel: ListViewModel,
    detailViewModel: DetailViewModel,
    navHostController: NavHostController,
) {

    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Jikan API App"
                    )
                }
            )
        },
        bottomBar = {
            if (currentRoute != Screen.Detail.route)
                BottomBar(
                    navHostController = navHostController
                )
        }
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route){
                val uiState = listViewModel.getAnimeCurrentSeasonFromApi().observeAsState()
                MangaListScreen(
                    navHostController = navHostController,
                    uiState = uiState
                )
            }
            composable(
                Screen.Detail.route,
                arguments = listOf(
                    navArgument("animeId") {
                        type = NavType.IntType
                    }
                )
            ) {
                val id = it.arguments?.getInt("animeId") ?: 0
                val detailState = detailViewModel.getAnimeDetailFromApi(id)
                    .observeAsState()
                MangaDetailScreen(
                    navHostController = navHostController,
                    uiState = detailState)
            }
            composable(
                route = Screen.About.route,
            ) {
                AboutScreen(
                    navHostController = navHostController,
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navHostController : NavHostController
){
    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar {
        val botNavItems: List<BottomNavItems> = listOf(
            BottomNavItems(
                name = "ListAnime",
                icons = Icons.Default.List,
                route = Screen.Home.route
            ), BottomNavItems(
                name = "About",
                icons = Icons.Default.Settings,
                route = Screen.About.route
            )
        )
        botNavItems.map {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = it.icons,
                        contentDescription = it.name
                    )
                },
                label = {
                    Text(
                        it.name
                    )
                },
                selected = currentRoute == it.route,
                onClick = {
                    navHostController.navigate(it.route)
                }
            )
        }
    }
}
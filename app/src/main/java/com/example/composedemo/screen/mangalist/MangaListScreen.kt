package com.example.composedemo.screen.mangalist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composedemo.data.Resource
import com.example.composedemo.data.model.Anime
import com.example.composedemo.ui.components.AnimeItem
import com.example.composedemo.ui.route.Screen

@Composable
fun MangaListScreen(
    modifier : Modifier = Modifier,
    navHostController: NavHostController,
    uiState: State<Resource<List<Anime>>?>
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(uiState.value){
            is Resource.Error -> {
                Text(
                    text = "Sorry, an error happened... :(",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Error Code :",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = uiState.value?.message.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600)
                )
            }
            is Resource.Loading -> {
                CircularProgressIndicator(
                    color = Color(0xFFa9a9a9),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 220.dp)
                )
                Text(
                    text = "Loading anime list...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top=250.dp)
                )
            }
            is Resource.Success -> {
                if (uiState.value?.data.isNullOrEmpty())
                    Text(
                        text = "Data Kosong!"
                    )
                else LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier.padding(bottom = 55.dp)
                ) {
                    items(items = uiState.value?.data!!){ anime ->
                        AnimeItem(anime = anime, onClick = {
                            navHostController.navigate(Screen.Detail.createRoute(it.id!!))
                        })
                    }
                }
            }
            else -> {
                Text(
                    text = "Data Kosong!",
                )
            }
        }
    }
}
package com.example.composedemo.ui.route

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{animeId}"){
        fun createRoute(animeId: Int) = "detail/$animeId"
    }
    object About : Screen("about")
}

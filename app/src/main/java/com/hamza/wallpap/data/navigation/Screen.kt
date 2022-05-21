package com.hamza.wallpap.data.navigation

sealed class Screen(val route: String){
    object Home: Screen("home_screen")
    object WallPaperScreen: Screen("wallpaper_screen/{regularUrl}/{fullUrl}")
    object Search: Screen("search_screen")
}

package com.hamza.wallpap.data.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home_screen","Home", Icons.Default.Home)
    object WallPaperScreen : Screen("wallpaper_screen/{regularUrl}/{fullUrl}","Wallpaper", Icons.Default.Wallpaper)
    object Search : Screen("search_screen","Search", Icons.Default.Search)
    object Settings : Screen("settings_screen","Settings", Icons.Default.Settings)
    object Hot : Screen("hot", "Hot", Icons.Default.Whatshot)
    object Favorite: Screen("favorite", "Favorite", Icons.Default.Favorite)
}

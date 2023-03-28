package com.hamza.wallpap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home_screen","Home", Icons.Default.Home)
    object Main : Screen("main_screen", "Main Screen", Icons.Default.Home)
    object WallPaperScreen : Screen("wallpaper_screen/{regularUrl}/{fullUrl}","Wallpaper", Icons.Default.Wallpaper)
    object FavouriteWallPaperScreen : Screen("fav_wallpaper_screen/{fullUrl}","Favourite Wallpaper", Icons.Default.Wallpaper)
    object Search : Screen("search_screen","Search", Icons.Default.Search)
    object Settings : Screen("settings_screen","Settings", Icons.Default.Settings)
    object Random : Screen("random", "Random", Icons.Default.AutoAwesome)
    object Favourite: Screen("favourite", "Favourite", Icons.Default.Favorite)

    // Custom Wallpaper
    object CustomWallpaperScreen: Screen("custom_wallpaper", "Custom Wallpaper", Icons.Default.Edit)

    //FireStore
    object Amoled: Screen("amoled", "Amoled", Icons.Default.Whatshot)
    object AmoledFullScreen : Screen("amoled_full_screen/{amoledUrl}","Amoled", Icons.Default.Wallpaper)
}

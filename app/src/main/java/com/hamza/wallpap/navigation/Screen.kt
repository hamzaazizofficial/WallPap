package com.hamza.wallpap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home_screen","Home", Icons.Default.Home)
    object Main : Screen("main_screen", "Main Screen", Icons.Default.Home)
    object HomeWallpaperFullScreen : Screen("home_wallpaper_screen/{regularUrl}/{fullUrl}","Wallpaper", Icons.Default.Wallpaper)
    object FavouriteFullScreen : Screen("fav_full_screen/{fullUrl}/{regularUrl}","Favourite Wallpaper", Icons.Default.Wallpaper)
    object Search : Screen("search_screen","Search", Icons.Default.Search)
    object Settings : Screen("settings_screen","Settings", Icons.Default.Settings)
    object Random : Screen("random", "Random", Icons.Default.AutoAwesome)
    object Favourite: Screen("favourite", "Favourite", Icons.Default.Favorite)

    // Editor
    object CustomWallpaperEditorScreen: Screen("custom_wallpaper_editor", "Custom Wallpaper", Icons.Default.Edit)

    object Latest: Screen("amoled", "Amoled", Icons.Default.Whatshot)
    object LatestFullScreen : Screen("latest_full_screen/{amoledUrl}","Amoled", Icons.Default.Wallpaper)
}

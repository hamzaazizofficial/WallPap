package com.hamza.wallpap.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.screens.home.HomeScreen
import com.hamza.wallpap.data.screens.search.SearchScreen
import com.hamza.wallpap.data.screens.settings.SettingsScreen
import com.hamza.wallpap.data.screens.wallpaper.WallpaperFullScreen


@OptIn(ExperimentalPagingApi::class)
@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.WallPaperScreen.route,
            arguments = listOf(
                navArgument("regularUrl") {
                    nullable = true
                    type = NavType.StringType
                },
                navArgument("fullUrl") {
                    nullable = true
                    type = NavType.StringType
                }
            )) {
            val regularUrl = it.arguments?.getString("regularUrl")
            val fullUrl = it.arguments?.getString("fullUrl")
            if (regularUrl != null && fullUrl != null) {
                WallpaperFullScreen(regularUrl, fullUrl)
            }
        }
    }
}
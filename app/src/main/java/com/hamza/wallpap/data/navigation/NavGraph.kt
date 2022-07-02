package com.hamza.wallpap.data.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.data.screens.favourite.FavouriteScreen
import com.hamza.wallpap.data.screens.home.HomeScreen
import com.hamza.wallpap.data.screens.home.HomeViewModel
import com.hamza.wallpap.data.screens.random.RandomScreen
import com.hamza.wallpap.data.screens.random.RandomScreenViewModel
import com.hamza.wallpap.data.screens.search.SearchScreen
import com.hamza.wallpap.data.screens.search.SearchViewModel
import com.hamza.wallpap.data.screens.settings.SettingsScreen
import com.hamza.wallpap.data.screens.settings.SettingsViewModel
import com.hamza.wallpap.data.screens.wallpaper.WallpaperFullScreen
import com.hamza.wallpap.ui.MainScreen

@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun NavGraph(navController: NavHostController, scaffoldState: ScaffoldState) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val randomScreenViewModel: RandomScreenViewModel = viewModel()
    val favUrlsViewModel: FavUrlsViewModel = viewModel()
//    val searchChipsViewModel: SearchChipsViewModel = hiltViewModel()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, homeViewModel, scaffoldState)
        }

        composable(Screen.Main.route) {
            MainScreen(navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController, searchViewModel, homeViewModel)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(settingsViewModel, navController, scaffoldState)
        }

        composable(Screen.Favourite.route) {
            FavouriteScreen(favUrlsViewModel,navController, scaffoldState)
        }

        composable(Screen.Random.route) {
            RandomScreen(navController, scaffoldState, randomScreenViewModel)
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
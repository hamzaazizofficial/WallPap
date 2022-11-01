package com.hamza.wallpap.data.navigation

//import androidx.navigation.compose.composable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.hamza.wallpap.data.screens.favourite.FavouriteWallpaperFullScreen
import com.hamza.wallpap.data.screens.firestore.amoled.AmoledFullScreen
import com.hamza.wallpap.data.screens.firestore.amoled.AmoledScreen
import com.hamza.wallpap.data.screens.firestore.amoled.AmoledViewModel
import com.hamza.wallpap.data.screens.home.HomeScreen
import com.hamza.wallpap.data.screens.home.HomeViewModel
import com.hamza.wallpap.data.screens.random.RandomScreen
import com.hamza.wallpap.data.screens.random.RandomScreenViewModel
import com.hamza.wallpap.data.screens.search.SearchScreen
import com.hamza.wallpap.data.screens.search.SearchViewModel
import com.hamza.wallpap.data.screens.settings.SettingsScreen
import com.hamza.wallpap.data.screens.settings.SettingsViewModel
import com.hamza.wallpap.data.screens.splash.SplashScreen
import com.hamza.wallpap.data.screens.wallpaper.WallpaperFullScreen
import com.hamza.wallpap.util.WallPapTheme

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class, ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPapTheme) -> Unit,
    currentRoute: String?
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val randomScreenViewModel: RandomScreenViewModel = viewModel()
    val favUrlsViewModel: FavUrlsViewModel = viewModel()
    val amoledViewModel: AmoledViewModel = viewModel()

    NavHost(
        navController, startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController, homeViewModel, scaffoldState)
        }

        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController, searchViewModel, homeViewModel)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(settingsViewModel, navController, scaffoldState, onItemSelected)
        }

        composable(Screen.Favourite.route) {
            FavouriteScreen(favUrlsViewModel, navController, scaffoldState)
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
                WallpaperFullScreen(regularUrl, fullUrl, navController)
            }
        }

        composable(Screen.FavouriteWallPaperScreen.route,
            arguments = listOf(
                navArgument("fullUrl") {
                    nullable = true
                    type = NavType.StringType
                },
                navArgument("regularUrl") {
                    nullable = true
                    type = NavType.StringType
                }
            )) {
            val fullUrl = it.arguments?.getString("fullUrl")
            if (fullUrl != null) {
                FavouriteWallpaperFullScreen(fullUrl, navController)
            }
        }

        // FireStore Screens
        composable(Screen.Amoled.route) {
            AmoledScreen(navController, amoledViewModel, scaffoldState)
        }

        composable(Screen.AmoledFullScreen.route,
            arguments = listOf(
                navArgument("amoledUrl") {
                    nullable = true
                    type = NavType.StringType
                }
            )) {
            val amoledUrl = it.arguments?.getString("amoledUrl")
            if (amoledUrl != null) {
                AmoledFullScreen(amoledUrl, navController)
            }
        }
    }
}
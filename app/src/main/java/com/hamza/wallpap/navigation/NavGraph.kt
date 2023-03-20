package com.hamza.wallpap.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperScreen
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.screens.favourite.FavouriteScreen
import com.hamza.wallpap.ui.screens.favourite.FavouriteWallpaperFullScreen
import com.hamza.wallpap.ui.screens.home.HomeScreen
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.screens.latest.LatestFullScreen
import com.hamza.wallpap.ui.screens.latest.LatestScreen
import com.hamza.wallpap.ui.screens.latest.LatestViewModel
import com.hamza.wallpap.ui.screens.random.RandomScreen
import com.hamza.wallpap.ui.screens.random.RandomScreenViewModel
import com.hamza.wallpap.ui.screens.search.SearchScreen
import com.hamza.wallpap.ui.screens.search.SearchViewModel
import com.hamza.wallpap.ui.screens.settings.SettingsScreen
import com.hamza.wallpap.ui.screens.settings.SettingsViewModel
import com.hamza.wallpap.ui.screens.splash.SplashScreen
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreen
import com.hamza.wallpap.util.WallPapTheme


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPapTheme) -> Unit,
    currentRoute: String?,
    context: Context,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val randomScreenViewModel: RandomScreenViewModel = viewModel()
    val favUrlsViewModel: FavUrlsViewModel = viewModel()
    val amoledViewModel: LatestViewModel = viewModel()
    val homeItems = homeViewModel.itemsFlow.collectAsLazyPagingItems()
    val randomItems = randomScreenViewModel.itemsFlow.collectAsLazyPagingItems()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val customWallpaperViewModel : CustomWallpaperViewModel = viewModel()

    NavHost(
        navController, startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController, homeViewModel, scaffoldState, homeItems, lazyStaggeredGridState)
        }

        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController, searchViewModel, homeViewModel, lazyStaggeredGridState)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(settingsViewModel, navController, scaffoldState, onItemSelected)
        }

        composable(Screen.Favourite.route) {
            FavouriteScreen(favUrlsViewModel, navController, scaffoldState, lazyStaggeredGridState)
        }

        composable(Screen.Random.route) {
            RandomScreen(navController, scaffoldState, randomScreenViewModel, randomItems, lazyStaggeredGridState)
        }

        composable(
            Screen.WallPaperScreen.route,
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

        composable(
            Screen.FavouriteWallPaperScreen.route,
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

        // Custom Wallpaper Screens
        composable(Screen.CustomWallpaperScreen.route) {
            CustomWallpaperScreen(navController, scaffoldState, customWallpaperViewModel, homeItems, context)
        }

        // FireStore Screens
        composable(Screen.Amoled.route) {
            LatestScreen(navController, amoledViewModel, scaffoldState)
        }

        composable(
            Screen.AmoledFullScreen.route,
            arguments = listOf(
                navArgument("amoledUrl") {
                    nullable = true
                    type = NavType.StringType
                }
            )) {
            val amoledUrl = it.arguments?.getString("amoledUrl")
            if (amoledUrl != null) {
                LatestFullScreen(amoledUrl, navController)
            }
        }
    }
}
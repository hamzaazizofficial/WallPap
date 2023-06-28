package com.hamza.wallpap.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
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
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreen
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreenViewModel
import com.hamza.wallpap.util.WallPapTheme
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoilApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)

@Composable
fun NavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPapTheme) -> Unit,
    currentRoute: String?,
    context: Context,
    scope: CoroutineScope,
    systemUiController: SystemUiController,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val wallpaperFullScreenViewModel: WallpaperFullScreenViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val randomScreenViewModel: RandomScreenViewModel = viewModel()
    val favUrlsViewModel: FavUrlsViewModel = viewModel()
    val latestViewModel: LatestViewModel = viewModel()
    val homeItems = homeViewModel.itemsFlow.collectAsLazyPagingItems()
    val refreshState =
        rememberSwipeRefreshState(isRefreshing = homeItems.loadState.refresh is LoadState.Loading)
    val randomItems = randomScreenViewModel.itemsFlow.collectAsLazyPagingItems()
    val favouriteItemsData = favUrlsViewModel.getAllFavUrls.observeAsState(listOf())
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val customWallpaperViewModel: CustomWallpaperViewModel = viewModel()

    AnimatedNavHost(
        navController, startDestination = Screen.Home.route,
    ) {

        composable(Screen.Home.route, enterTransition = {
            when (currentRoute) {
                Screen.Home.route -> fadeIn(animationSpec = tween(600))
                else -> null
            }
        }) {
            HomeScreen(
                navController,
                homeViewModel,
                scaffoldState,
                homeItems,
                lazyStaggeredGridState,
                refreshState,
                context,
                scope,
                systemUiController
            )
        }

        composable(Screen.Search.route, enterTransition = {
            when (currentRoute) {
                Screen.Search.route -> fadeIn(animationSpec = tween(600)) + slideInVertically { 1800 }
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.Search.route -> fadeOut(animationSpec = tween(600)) + slideOutVertically { 1800 }
                else -> null
            }
        }) {
            SearchScreen(
                navController = navController,
                searchViewModel,
                homeViewModel,
                lazyStaggeredGridState,
                homeItems,
                refreshState,
                context
            )
        }

        composable(Screen.Settings.route, enterTransition = {
            when (currentRoute) {
                Screen.Settings.route -> fadeIn(animationSpec = tween(600)) + slideInHorizontally { 1800 }
                else -> null
            }
        }) {
            SettingsScreen(
                settingsViewModel,
                navController,
                scaffoldState,
                onItemSelected,
                systemUiController,
                context,
                scope
            )
        }

        composable(Screen.Favourite.route) {
            FavouriteScreen(
                favUrlsViewModel, navController, context, favouriteItemsData, systemUiController
            )
        }

        composable(Screen.Random.route) {
            RandomScreen(
                navController,
                scaffoldState,
                randomScreenViewModel,
                randomItems,
                lazyStaggeredGridState,
                systemUiController,
                context,
                scope
            )
        }

        composable(Screen.HomeWallpaperFullScreen.route, enterTransition = {
            when (currentRoute) {
                Screen.HomeWallpaperFullScreen.route -> fadeIn(animationSpec = tween(600)) + scaleIn()
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.HomeWallpaperFullScreen.route -> fadeOut(animationSpec = tween(600)) + scaleOut()
                else -> null
            }
        }, arguments = listOf(navArgument("regularUrl") {
            nullable = true
            type = NavType.StringType
        }, navArgument("fullUrl") {
            nullable = true
            type = NavType.StringType
        })) {
            val regularUrl = it.arguments?.getString("regularUrl")
            val fullUrl = it.arguments?.getString("fullUrl")
            if (regularUrl != null && fullUrl != null) {
                WallpaperFullScreen(
                    regularUrl,
                    fullUrl,
                    navController,
                    favUrlsViewModel,
                    wallpaperFullScreenViewModel,
                    scope,
                    systemUiController,
                    context
                )
            }
        }

        composable(Screen.FavouriteFullScreen.route, enterTransition = {
            when (currentRoute) {
                Screen.FavouriteFullScreen.route -> fadeIn(animationSpec = tween(600)) + scaleIn()
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.FavouriteFullScreen.route -> fadeOut(animationSpec = tween(600)) + scaleOut()
                else -> null
            }
        }, arguments = listOf(navArgument("fullUrl") {
            nullable = true
            type = NavType.StringType
        }, navArgument("regularUrl") {
            nullable = true
            type = NavType.StringType
        })) {
            val fullUrl = it.arguments?.getString("fullUrl")
            val regularUrl = it.arguments?.getString("regularUrl")
            if (fullUrl != null && regularUrl != null) {
                FavouriteWallpaperFullScreen(
                    fullUrl,
                    regularUrl,
                    navController,
                    favUrlsViewModel,
                    wallpaperFullScreenViewModel,
                    context,
                    scope
                )
            }
        }

        // Custom Wallpaper Screens
        composable(Screen.CustomWallpaperEditorScreen.route) {
            CustomWallpaperScreen(
                navController, customWallpaperViewModel, homeItems, context, systemUiController
            )
        }

        // FireStore Screens
        composable(Screen.Latest.route) {
            LatestScreen(navController, latestViewModel, scaffoldState, systemUiController)
        }

        composable(Screen.LatestFullScreen.route, enterTransition = {
            when (currentRoute) {
                Screen.LatestFullScreen.route -> fadeIn(animationSpec = tween(600)) + scaleIn()
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.LatestFullScreen.route -> fadeOut(animationSpec = tween(600)) + scaleOut()
                else -> null
            }
        }, arguments = listOf(navArgument("amoledUrl") {
            nullable = true
            type = NavType.StringType
        })) {
            val amoledUrl = it.arguments?.getString("amoledUrl")
            if (amoledUrl != null) {
                LatestFullScreen(
                    amoledUrl, navController, wallpaperFullScreenViewModel, context, scope
                )
            }
        }
    }
}
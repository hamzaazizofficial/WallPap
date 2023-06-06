package com.hamza.wallpap.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.navigation.NavGraph
import com.hamza.wallpap.navigation.Screen
import com.hamza.wallpap.ui.screens.common.BottomBar
import com.hamza.wallpap.ui.screens.common.NavDrawer
import com.hamza.wallpap.ui.screens.common.TopBar
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.screens.random.RandomScreenViewModel
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.util.WallPapTheme
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagingApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    randomScreenViewModel: RandomScreenViewModel = hiltViewModel(),
    favUrlsViewModel: FavUrlsViewModel = hiltViewModel(),
    customWallpaperViewModel: CustomWallpaperViewModel = hiltViewModel(),
    onItemSelected: (WallPapTheme) -> Unit,
) {
    val refreshTrigger = remember { mutableStateOf(0) }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (
                currentRoute.equals(Screen.Home.route) or
                currentRoute.equals(Screen.Random.route) or
                currentRoute.equals(Screen.Latest.route) or
                currentRoute.equals(Screen.Favourite.route) or
                currentRoute.equals(Screen.Settings.route) or
                currentRoute.equals(Screen.CustomWallpaperEditorScreen.route)
            ) {
                TopBar(
                    onNavButtonClick = {
                        if (!currentRoute.equals(Screen.CustomWallpaperEditorScreen.route) && !currentRoute.equals(
                                Screen.Settings.route
                            )
                        ) {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        } else {
                            navController.popBackStack()
                        }
                    },
                    currentRoute,
                    onSearchClicked = {
                        navController.navigate(Screen.Search.route)
                    },
                    onUserDetailsClicked = {
                        if (currentRoute.equals(Screen.Home.route)) {
                            homeViewModel.showUserDetails = !homeViewModel.showUserDetails
                        }
                        if (currentRoute.equals(Screen.Random.route)) {
                            randomScreenViewModel.showUserDetails =
                                !randomScreenViewModel.showUserDetails
                        }
                    },
                    onRefreshClicked = {
                        refreshTrigger.value++
                    },
                    homeViewModel,
                    randomScreenViewModel,
                    favUrlsViewModel,
                    context,
                    customWallpaperViewModel
                )
            }
        },
        bottomBar = {
            if (
                currentRoute.equals(Screen.Home.route) or
                currentRoute.equals(Screen.Random.route) or
                currentRoute.equals(Screen.Latest.route) or
                currentRoute.equals(Screen.Favourite.route)
            ) {
                BottomBar(navController = navController)
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavDrawer(scaffoldState = scaffoldState, navController, scope)
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            NavGraph(
                navController = navController,
                scaffoldState,
                onItemSelected,
                currentRoute,
                context,
                scope
            )
        }
    }
}
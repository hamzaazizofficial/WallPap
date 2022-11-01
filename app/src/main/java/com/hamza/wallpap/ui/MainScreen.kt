package com.hamza.wallpap.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.data.navigation.NavGraph
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.data.screens.common.BottomBar
import com.hamza.wallpap.data.screens.common.NavDrawer
import com.hamza.wallpap.data.screens.common.TopBar
import com.hamza.wallpap.data.screens.home.HomeViewModel
import com.hamza.wallpap.data.screens.random.RandomScreenViewModel
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
    onItemSelected: (WallPapTheme) -> Unit
) {
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
                currentRoute.equals(Screen.Amoled.route) or
                currentRoute.equals(Screen.Favourite.route) or
                currentRoute.equals(Screen.Settings.route)
            ) {
                TopBar(
                    onNavButtonClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
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
                    homeViewModel,
                    randomScreenViewModel,
                    favUrlsViewModel,
                    context
                )
            }
        },
        bottomBar = {
            if (
                currentRoute.equals(Screen.Home.route) or
                currentRoute.equals(Screen.Random.route) or
                currentRoute.equals(Screen.Amoled.route) or
                currentRoute.equals(Screen.Favourite.route) or
                currentRoute.equals(Screen.Settings.route)
            ) {
                BottomBar(navController = navController)
            }
        },
        drawerContent = {
            NavDrawer(scaffoldState = scaffoldState)
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            NavGraph(navController = navController, scaffoldState, onItemSelected, currentRoute)
        }
    }
}
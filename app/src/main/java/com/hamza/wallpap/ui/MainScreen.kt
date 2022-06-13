package com.hamza.wallpap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.navigation.NavGraph
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.data.screens.home.BottomBar
import com.hamza.wallpap.data.screens.home.TopBar
import com.hamza.wallpap.data.screens.home.HomeViewModel

@OptIn(ExperimentalPagingApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (
                !currentRoute.equals(Screen.Search.route) &&
                !currentRoute.equals(Screen.WallPaperScreen.route)
            ) {
                TopBar(
                    currentRoute,
                    onSearchClicked = {
                        navController.navigate(Screen.Search.route)
                    },
                    onUserDetailsClicked = {
                        homeViewModel.showUserDetails = !homeViewModel.showUserDetails
                    },
                    homeViewModel
                )
            }
        },
        bottomBar = {
            if (!currentRoute.equals(Screen.Search.route) && !currentRoute.equals(Screen.WallPaperScreen.route)) {
                BottomBar(navController = navController)
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            NavGraph(navController = navController)
        }
    }
}
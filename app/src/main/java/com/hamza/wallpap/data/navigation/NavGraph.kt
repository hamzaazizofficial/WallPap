package com.hamza.wallpap.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hamza.wallpap.data.screens.home.HomeScreen


@Composable
fun NavGraph(navController: NavHostController) {

//    val emailViewModel: EmailViewModel = viewModel()
//    val homeViewModel: HomeViewModel = viewModel()
//    val inboxViewModel: InboxViewModel = viewModel()
//    val settingsViewModel: SettingsViewModel = viewModel()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Search.route){
//            SettingsScreen(settingsViewModel)
        }
    }
}
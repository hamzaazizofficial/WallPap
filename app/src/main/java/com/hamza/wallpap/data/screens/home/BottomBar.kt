package com.hamza.wallpap.data.screens.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.paging.ExperimentalPagingApi

@OptIn(ExperimentalPagingApi::class)
@Composable
fun BottomBar(homeViewModel: HomeViewModel, onSettingsClick: () -> Unit) {

//    val bottomBarSelectedItem = remember { mutableStateOf("home") }

    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ) {

        BottomNavigationItem(

            icon = {
                Icon(Icons.Filled.Home, contentDescription = "")
            },
            label = { Text(text = "Home") },
            selected = homeViewModel.bottomBarSelectedItem == "home", onClick = {
                homeViewModel.bottomBarSelectedItem = "home"
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Whatshot, null,
                )
            },
            label = { Text(text = "Hot") },
            selected = homeViewModel.bottomBarSelectedItem == "hot",
            onClick = {
                homeViewModel.bottomBarSelectedItem = "hot"
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Favorite, "",
                )
            },
            label = { Text(text = "Favorite") },
            selected = homeViewModel.bottomBarSelectedItem == "favorite",
            onClick = {
                homeViewModel.bottomBarSelectedItem = "favorite"
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Settings, "",
                )
            },
            label = { Text(text = "Settings") },
            selected = homeViewModel.bottomBarSelectedItem == "settings",
            onClick =
//                homeViewModel.bottomBarSelectedItem = "settings"
                onSettingsClick
            ,
            alwaysShowLabel = false
        )
    }
}
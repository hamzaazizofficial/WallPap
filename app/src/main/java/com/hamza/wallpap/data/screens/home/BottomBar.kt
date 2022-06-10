package com.hamza.wallpap.data.screens.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hamza.wallpap.data.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val items = listOf(
        Screen.Home,
        Screen.Hot,
        Screen.Favourite,
        Screen.Settings,
    )
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        BottomNavigation(
            backgroundColor = Color.Black,
            contentColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = item.icon, item.title)
                    },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route)
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}

package com.hamza.wallpap.data.screens.common

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val items = listOf(
        Screen.Home,
        Screen.Amoled,
        Screen.Random,
        Screen.Favourite,
        Screen.Settings,
    )
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.bottomAppBarBackgroundColor,
            contentColor = MaterialTheme.colors.bottomAppBarContentColor
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    unselectedContentColor = Color.LightGray,
                    icon = {
                        Icon(imageVector = item.icon, item.title)
                    },
//                    label = { Text(text = item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route)
                        }
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}

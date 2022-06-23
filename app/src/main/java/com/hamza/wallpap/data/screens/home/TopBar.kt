package com.hamza.wallpap.data.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.data.screens.hot.HotViewModel
import com.hamza.wallpap.ui.theme.topAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.topAppBarContentColor

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TopBar(
    onNavButtonClick: () -> Unit = {},
    currentRoute: String?,
    onSearchClicked: () -> Unit,
    onUserDetailsClicked: () -> Unit,
    homeViewModel: HomeViewModel,
    hotViewModel: HotViewModel
) {
    TopAppBar(
        title = {
            Text(
                text =
                if (currentRoute.equals(Screen.Home.route)) "Home"
                else if (currentRoute.equals(Screen.Settings.route)) "Settings"
                else if (currentRoute.equals(Screen.Hot.route)) "Hot"
                else if (currentRoute.equals(Screen.Favourite.route)) "Favourite"
                else "Home",
                color = MaterialTheme.colors.topAppBarContentColor,
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            IconButton(onClick = onNavButtonClick) {
                Icon(
                    imageVector = Icons.Default.Dehaze,
                    contentDescription = null
                )
            }
        },
        actions = {

            if (!currentRoute.equals(Screen.Settings.route) && !currentRoute.equals(Screen.Favourite.route)) {
                IconButton(onClick = onUserDetailsClicked) {
                    if (currentRoute.equals(Screen.Home.route)) {
                        Icon(
                            imageVector = if (homeViewModel.showUserDetails) Icons.Outlined.AccountCircle else Icons.Default.AccountCircle,
                            contentDescription = "Show user details icon"
                        )
                    }
                    if (currentRoute.equals(Screen.Hot.route)) {
                        Icon(
                            imageVector = if (hotViewModel.showUserDetails) Icons.Outlined.AccountCircle else Icons.Default.AccountCircle,
                            contentDescription = "Show user details icon"
                        )
                    }
                }

                IconButton(onClick = onSearchClicked) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        }
    )
}

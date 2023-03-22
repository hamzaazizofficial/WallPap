package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.navigation.Screen
import com.hamza.wallpap.ui.screens.editor.ClearEditorDialog
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.screens.random.RandomScreenViewModel
import com.hamza.wallpap.ui.theme.topAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.topAppBarContentColor
import com.hamza.wallpap.ui.theme.topAppBarTitle
import com.hamza.wallpap.util.shareWallpaper

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagingApi::class, ExperimentalAnimationApi::class)
@Composable
fun TopBar(
    onNavButtonClick: () -> Unit = {},
    currentRoute: String?,
    onSearchClicked: () -> Unit,
    onUserDetailsClicked: () -> Unit,
    homeViewModel: HomeViewModel,
    randomScreenViewModel: RandomScreenViewModel,
    favUrlsViewModel: FavUrlsViewModel,
    context: Context,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {
    if (customWallpaperViewModel.clearEditorDialogState.value) {
        ClearEditorDialog(
            dialogState = customWallpaperViewModel.clearEditorDialogState,
            context = context,
            customWallpaperViewModel
        )
    }

    TopAppBar(
        title = {
            Text(
                text =
                if (currentRoute.equals(Screen.Home.route)) "Home"
                else if (currentRoute.equals(Screen.Settings.route)) "Settings"
                else if (currentRoute.equals(Screen.Random.route)) "Random"
                else if (currentRoute.equals(Screen.Favourite.route)) "Favourite"
                else if (currentRoute.equals(Screen.Amoled.route)) "Latest"
                else if (currentRoute.equals(Screen.CustomWallpaperScreen.route)) "Editor"
                else "Home",
                color = MaterialTheme.colors.topAppBarTitle,
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            if (!currentRoute.equals(Screen.CustomWallpaperScreen.route)) {
                IconButton(onClick = onNavButtonClick) {
                    Icon(
                        imageVector = Icons.Default.Dehaze,
                        contentDescription = null,
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            } else {
                IconButton(onClick = onNavButtonClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            }
        },
        actions = {

            if (!currentRoute.equals(Screen.Settings.route)) {

                if (currentRoute.equals(Screen.Home.route)) {
                    IconButton(onClick = onUserDetailsClicked) {
                        Icon(
                            imageVector = if (homeViewModel.showUserDetails) Icons.Default.Info else Icons.Outlined.Info,
                            contentDescription = "Show user details icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }
                if (currentRoute.equals(Screen.Random.route)) {
                    IconButton(onClick = onUserDetailsClicked) {
                        Icon(
                            imageVector = if (randomScreenViewModel.showUserDetails) Icons.Default.Info else Icons.Outlined.Info,
                            contentDescription = "Show user details icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                if (currentRoute.equals(Screen.Favourite.route)) {
                    IconButton(onClick = {
                        favUrlsViewModel.deleteAllFavouriteUrls()
                        Toast.makeText(context, "Removed all images!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Show user details icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                AnimatedVisibility(
                    visible = currentRoute.equals(Screen.CustomWallpaperScreen.route) && customWallpaperViewModel.savedImageBitmap.value != null
                            && customWallpaperViewModel.shareWallpaperVisible.value,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    IconButton(onClick = {
                        shareWallpaper(context, customWallpaperViewModel.savedImageBitmap.value)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share wallpaper",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                AnimatedVisibility(
                    visible = currentRoute.equals(Screen.CustomWallpaperScreen.route) && (customWallpaperViewModel.bgImageUrl.value != null
                            || customWallpaperViewModel.boxColor.value != Color(0xF1FFFFFF)
                            || customWallpaperViewModel.wallpaperText.value != ""),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {

                    IconButton(
                        onClick = {
                            customWallpaperViewModel.clearEditorDialogState.value = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Clear Screen",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                if (!currentRoute.equals(Screen.Settings.route) && !currentRoute.equals(Screen.CustomWallpaperScreen.route) && !currentRoute.equals(
                        Screen.Favourite.route
                    )
                ) {
                    IconButton(onClick = onSearchClicked) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }
            }
        }
    )
}

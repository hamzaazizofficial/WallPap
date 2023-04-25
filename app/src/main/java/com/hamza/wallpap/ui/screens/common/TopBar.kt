package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.navigation.Screen
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.screens.random.RandomScreenViewModel
import com.hamza.wallpap.ui.theme.*

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
        ClearAllDialog(
            dialogState = customWallpaperViewModel.clearEditorDialogState,
            context = context,
            customWallpaperViewModel,
            "Are you sure want to clear the screen?",
            currentRoute,
            favUrlsViewModel
        )
    }

    if (favUrlsViewModel.clearAllImagesDialogState.value) {
        ClearAllDialog(
            dialogState = favUrlsViewModel.clearAllImagesDialogState,
            context = context,
            customWallpaperViewModel,
            "Are you sure want to remove all images?",
            currentRoute,
            favUrlsViewModel
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
                else if (currentRoute.equals(Screen.Latest.route)) "Latest"
                else if (currentRoute.equals(Screen.CustomWallpaperEditorScreen.route)) "Editor"
                else "Home",
                color = MaterialTheme.colors.topAppBarTitle,
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            if (!currentRoute.equals(Screen.CustomWallpaperEditorScreen.route) && !currentRoute.equals(
                    Screen.Settings.route
                )
            ) {
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

                if (currentRoute.equals(Screen.Favourite.route) &&
                    favUrlsViewModel.getAllFavUrls.observeAsState(listOf()).value.isNotEmpty()
                ) {
                    IconButton(
                        onClick = {
                            favUrlsViewModel.clearAllImagesDialogState.value = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Show user details icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                AnimatedVisibility(
                    visible = currentRoute.equals(Screen.CustomWallpaperEditorScreen.route) &&
                            (customWallpaperViewModel.bgImageFullUrl.value != null
                                    || customWallpaperViewModel.bgBoxColor.value != Color(0xF1FFFFFF)
                                    || customWallpaperViewModel.wallpaperText.value != ""
                                    || customWallpaperViewModel.bgImageUri.value != null),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            customWallpaperViewModel.clearEditorDialogState.value = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.ClearAll,
                            contentDescription = "Clear Screen",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                AnimatedVisibility(
                    visible = currentRoute.equals(Screen.CustomWallpaperEditorScreen.route) &&
                            (customWallpaperViewModel.bgImageFullUrl.value != null
                                    || customWallpaperViewModel.bgImageUri.value != null),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {

                    IconToggleButton(
                        checked = customWallpaperViewModel.editorDropDownExpanded.value,
                        onCheckedChange = {
                            customWallpaperViewModel.editorDropDownExpanded.value = it
                        }) {
                        if (customWallpaperViewModel.editorDropDownExpanded.value) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropUp,
                                contentDescription = null,
                                tint = if ((customWallpaperViewModel.saturationSliderValue.value != 1f && customWallpaperViewModel.saturationSliderPosition.value != 1f) || customWallpaperViewModel.editorDropDownExpanded.value) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.iconColor
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = if (customWallpaperViewModel.saturationSliderValue.value != 1f && customWallpaperViewModel.saturationSliderPosition.value != 1f) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.iconColor
                            )
                        }
                    }
                }

                if (!currentRoute.equals(Screen.Settings.route) &&
                    !currentRoute.equals(Screen.CustomWallpaperEditorScreen.route) &&
                    !currentRoute.equals(Screen.Favourite.route)
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

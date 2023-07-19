package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.R
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.navigation.Screen
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.screens.latest.LatestViewModel
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
    latestViewModel: LatestViewModel,
) {

    if (customWallpaperViewModel.clearEditorDialogState.value) {
        ClearAllDialog(
            dialogState = customWallpaperViewModel.clearEditorDialogState,
            context = context,
            customWallpaperViewModel,
            stringResource(id = R.string.clear_screen),
            currentRoute,
            favUrlsViewModel
        )
    }

    if (favUrlsViewModel.clearAllImagesDialogState.value) {
        ClearAllDialog(
            dialogState = favUrlsViewModel.clearAllImagesDialogState,
            context = context,
            customWallpaperViewModel,
            stringResource(id = R.string.clear_images),
            currentRoute,
            favUrlsViewModel
        )
    }

    TopAppBar(
        elevation = if (currentRoute.equals(Screen.CustomWallpaperEditorScreen.route)) 0.dp else 5.dp,
        title = {
            Text(
                text =
                if (currentRoute.equals(Screen.Home.route)) stringResource(id = R.string.home)
                else if (currentRoute.equals(Screen.Settings.route)) stringResource(id = R.string.settings)
//                else if (currentRoute.equals(Screen.Random.route)) stringResource(id = R.string.random)
                else if (currentRoute.equals(Screen.Favourite.route)) stringResource(id = R.string.favourite)
                else if (currentRoute.equals(Screen.Latest.route)) stringResource(id = R.string.latest)
                else if (currentRoute.equals(Screen.CustomWallpaperEditorScreen.route)) stringResource(
                    id = R.string.editor
                )
                else stringResource(id = R.string.home),
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

//                if (currentRoute.equals(Screen.Home.route)) {
//                    IconButton(onClick = onUserDetailsClicked) {
//                        Icon(
//                            imageVector = if (homeViewModel.showUserDetails) Icons.Default.Info else Icons.Outlined.Info,
//                            contentDescription = stringResource(id = R.string.show_user_details),
//                            tint = MaterialTheme.colors.topAppBarContentColor
//                        )
//                    }
//                }
//                if (currentRoute.equals(Screen.Random.route)) {
//                    IconButton(onClick = onUserDetailsClicked) {
//                        Icon(
//                            imageVector = if (randomScreenViewModel.showUserDetails) Icons.Default.Info else Icons.Outlined.Info,
//                            contentDescription = stringResource(id = R.string.show_user_details),
//                            tint = MaterialTheme.colors.topAppBarContentColor
//                        )
//                    }
//                }

                if (currentRoute.equals(Screen.Favourite.route) &&
                    favUrlsViewModel.getAllFavUrls.observeAsState(listOf()).value.isNotEmpty()
                ) {
                    IconButton(
                        onClick = {
                            favUrlsViewModel.clearAllImagesDialogState.value = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = stringResource(id = R.string.clear_images_button),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                if (currentRoute.equals(Screen.CustomWallpaperEditorScreen.route)) {
                    AnimatedVisibility(
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
                                contentDescription = stringResource(id = R.string.clear_screen_button),
                                tint = MaterialTheme.colors.topAppBarContentColor
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = (customWallpaperViewModel.bgImageFullUrl.value != null || customWallpaperViewModel.bgImageUri.value != null),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    )
                    {
                        IconToggleButton(
                            checked = customWallpaperViewModel.editorDropDownExpanded.value,
                            onCheckedChange = {
                                customWallpaperViewModel.editorDropDownExpanded.value = it
                            }) {
                            if (customWallpaperViewModel.editorDropDownExpanded.value) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = stringResource(id = R.string.done),
                                    tint = if (
                                        (customWallpaperViewModel.saturationSliderValue.value != 1f && customWallpaperViewModel.saturationSliderPosition.value != 1f) || customWallpaperViewModel.editorDropDownExpanded.value) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.iconColor
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.InvertColors,
                                    contentDescription = stringResource(id = R.string.colorize),
                                    tint = if (customWallpaperViewModel.saturationSliderValue.value != 1f && customWallpaperViewModel.saturationSliderPosition.value != 1f) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.iconColor
                                )
                            }
                        }
                    }

                }

                if (!currentRoute.equals(Screen.Settings.route) &&
                    !currentRoute.equals(Screen.CustomWallpaperEditorScreen.route) &&
                    !currentRoute.equals(Screen.Favourite.route) &&
                    !currentRoute.equals(Screen.Latest.route)
                ) {
                    IconButton(onClick = onSearchClicked) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                if (currentRoute.equals(Screen.Latest.route)){
                    IconButton(onClick = {
                        latestViewModel.shuffleImages()
                    }) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = stringResource(id = R.string.shuffle),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }
            }
        }
    )
}

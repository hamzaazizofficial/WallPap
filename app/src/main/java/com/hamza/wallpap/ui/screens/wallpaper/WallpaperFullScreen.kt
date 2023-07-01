package com.hamza.wallpap.ui.screens.wallpaper

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.systemuicontroller.SystemUiController
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.screens.common.SetWallpaperDialog
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.iconColor
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.util.*
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun WallpaperFullScreen(
    regularUrl: String,
    fullUrl: String,
    navController: NavHostController,
    favUrlsViewModel: FavUrlsViewModel,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    scope: CoroutineScope,
    systemUiController: SystemUiController,
    context: Context,
) {
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val configuration = LocalConfiguration.current
    var expanded by remember { mutableStateOf(false) }

    val matrix by remember { mutableStateOf(ColorMatrix()) }
    matrix.setToSaturation(wallpaperFullScreenViewModel.saturationSliderValue.value)
    val colorFilter = ColorFilter.colorMatrix(matrix)

    var smallSizeImage by remember { mutableStateOf<Bitmap?>(null) }
    var originalImage by remember { mutableStateOf<Bitmap?>(null) }
    var finalImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val snackBarHostState = remember { androidx.compose.material3.SnackbarHostState() }


    DisposableEffect(Unit) {
        val activity = context as? ComponentActivity
        activity?.requestedOrientation = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            Configuration.ORIENTATION_PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            else -> activity?.requestedOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    if (wallpaperFullScreenViewModel.setOriginalWallpaperDialog.value) {
        SetWallpaperDialog(
            dialogState = wallpaperFullScreenViewModel.setOriginalWallpaperDialog,
            context = context,
            wallpaperFullScreenViewModel,
            fullUrl,
            finalImageBitmap
        )
    }

    LaunchedEffect(key1 = "bitmap", block = {
        originalImage = getBitmapFromUrl(fullUrl)
        smallSizeImage = loadReducedSizeBitmapFromUrl(fullUrl)
        finalImageBitmap = originalImage
    })

    val captureController = rememberCaptureController()

    BackHandler {
        navController.popBackStack()
        wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
        wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
    }

    androidx.compose.material3.Scaffold(snackbarHost = {
        androidx.compose.material3.SnackbarHost(
            hostState = snackBarHostState
        )
    }) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            Capturable(controller = captureController, onCaptured = { bitmap, _ ->
                if (bitmap != null) {
                    finalImageBitmap = bitmap.asAndroidBitmap()
                }
            }, content = {
                SubcomposeAsyncImage(
                    model = smallSizeImage,
                    contentScale = wallpaperFullScreenViewModel.scale,
                    contentDescription = null,
                    colorFilter = colorFilter
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                contentScale = wallpaperFullScreenViewModel.scale,
                                model = regularUrl,
                                contentDescription = null
                            )
                            LinearProgressIndicator(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    } else {
                        SubcomposeAsyncImageContent(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            })

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(ContentAlpha.medium)
                    .align(Alignment.TopEnd)
                    .animateContentSize(),
                color = Color.Black
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick = {
                                navController.popBackStack()
                                wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
                                wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
                            }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        Row {
                            AnimatedVisibility(
                                visible = !expanded,
                                enter = scaleIn() + fadeIn(),
                                exit = scaleOut() + fadeOut()
                            ) {
                                if ((wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f &&
                                            wallpaperFullScreenViewModel.saturationSliderValue.value != 1f)
                                ) {
                                    IconButton(
                                        onClick = {
                                            captureController.capture()
                                            finalImageBitmap?.let {
                                                saveMediaToStorage(
                                                    it,
                                                    context
                                                )
                                            }
                                            scope.launch {
                                                snackBarHostState.showSnackbar(
                                                    "Saved to Gallery!",
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Short
                                                )
//                                                if (interstitialAd.isReady) {
//                                                    interstitialAd.showAd()
//                                                }
                                            }
                                        }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Download,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                } else {
                                    IconButton(
                                        onClick = {
                                            finalImageBitmap?.let {
                                                saveMediaToStorage(
                                                    it,
                                                    context
                                                )
                                            }
                                            scope.launch {
                                                snackBarHostState.showSnackbar(
                                                    "Saved to Gallery!",
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Download,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                            if (wallpaperFullScreenViewModel.showFitScreenBtn) {
                                IconButton(
                                    onClick = {
                                        wallpaperFullScreenViewModel.scale = ContentScale.Fit
                                        wallpaperFullScreenViewModel.showFitScreenBtn = false
                                        wallpaperFullScreenViewModel.showCropScreenBtn = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Fullscreen,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }

                            if (wallpaperFullScreenViewModel.showCropScreenBtn) {
                                IconButton(
                                    onClick = {
                                        wallpaperFullScreenViewModel.scale = ContentScale.Crop
                                        wallpaperFullScreenViewModel.showCropScreenBtn = false
                                        wallpaperFullScreenViewModel.showFitScreenBtn = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Fullscreen,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.bottomAppBarContentColor
                                    )
                                }
                            }

                            if (expanded) {
                                IconButton(
                                    onClick = {
                                        expanded =
                                            if (wallpaperFullScreenViewModel.saturationSliderValue.value == 1f && wallpaperFullScreenViewModel.saturationSliderPosition.value == 1f) {
                                                false
                                            } else {
                                                captureController.capture()
                                                false
                                            }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null,
                                        tint = if ((wallpaperFullScreenViewModel.saturationSliderValue.value != 1f && wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f) || expanded) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.iconColor
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        expanded = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.InvertColors,
                                        contentDescription = null,
                                        tint = if (wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f) MaterialTheme.colors.bottomAppBarContentColor else Color.White
                                    )
                                }
                            }
                        }
                    }
                    AnimatedVisibility(visible = expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 0.dp, bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                androidx.compose.material3.Slider(
                                    modifier = Modifier.weight(4.5f),
                                    value = wallpaperFullScreenViewModel.saturationSliderPosition.value,
                                    onValueChange = {
                                        wallpaperFullScreenViewModel.saturationSliderPosition.value =
                                            it
                                    },
                                    valueRange = 0f..10f,
                                    onValueChangeFinished = {
                                        wallpaperFullScreenViewModel.saturationSliderValue.value =
                                            wallpaperFullScreenViewModel.saturationSliderPosition.value
                                    },
                                    colors = androidx.compose.material3.SliderDefaults.colors(
                                        activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(
                                            0.5f
                                        ),
                                        thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                                    )
                                )

                                IconButton(onClick = {
                                    wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
                                    wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
                                    finalImageBitmap = originalImage
                                }, modifier = Modifier.weight(1f)) {
                                    Icon(
                                        imageVector = if (wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f && wallpaperFullScreenViewModel.saturationSliderValue.value != 1f) Icons.Default.InvertColorsOff else Icons.Default.InvertColors,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = !expanded,
                enter = slideInVertically { 3000 } + scaleIn(),
                exit = slideOutVertically { 500 }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    FloatingActionButton(
                        onClick = {
                            shareWallpaper(
                                context,
                                finalImageBitmap,
                                shareWithWhatsAppOnly = false,
                                saveToDrive = false
                            )
                        },
                        modifier = Modifier
                            .padding(8.dp),
                        backgroundColor = MaterialTheme.colors.bottomAppBarBackgroundColor
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    }

                    AnimatedVisibility(
                        visible = wallpaperFullScreenViewModel.saturationSliderPosition.value == 1f && wallpaperFullScreenViewModel.saturationSliderValue.value == 1f,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        FloatingActionButton(
                            onClick = {
                                wallpaperFullScreenViewModel.id += 1
                                val favUrl =
                                    FavouriteUrls(
                                        wallpaperFullScreenViewModel.id,
                                        fullUrl,
                                        regularUrl
                                    )
                                favUrlsViewModel.addToFav(favUrl)
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        "Added to Favourites!",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp),
                            backgroundColor = MaterialTheme.colors.bottomAppBarBackgroundColor
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = MaterialTheme.colors.bottomAppBarContentColor
                            )
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            wallpaperFullScreenViewModel.setOriginalWallpaperDialog.value = true
                        },
                        modifier = Modifier
                            .padding(8.dp),
                        backgroundColor = MaterialTheme.colors.bottomAppBarBackgroundColor
                    ) {
                        Icon(
                            imageVector = Icons.Default.Wallpaper,
                            contentDescription = null,
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    }
                }
            }
        }
    }
}

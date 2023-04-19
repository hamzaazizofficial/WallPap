package com.hamza.wallpap.ui.screens.wallpaper

import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.screens.common.SetWallpaperDialog
import com.hamza.wallpap.ui.screens.common.admob.MainInterstitialAd
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.util.getBitmapFromUrl
import com.hamza.wallpap.util.loadReducedSizeBitmapFromUrl
import com.hamza.wallpap.util.saveMediaToStorage
import com.hamza.wallpap.util.shareWallpaper
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun WallpaperFullScreen(
    regularUrl: String,
    fullUrl: String,
    navController: NavHostController,
    currentRoute: String?,
    favUrlsViewModel: FavUrlsViewModel,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    var expanded by remember { mutableStateOf(false) }

    val matrix by remember { mutableStateOf(ColorMatrix()) }
    matrix.setToSaturation(wallpaperFullScreenViewModel.saturationSliderValue.value)
    val colorFilter = ColorFilter.colorMatrix(matrix)

    var smallSizeImage by remember { mutableStateOf<Bitmap?>(null) }
    var originalImage by remember { mutableStateOf<Bitmap?>(null) }

    if (wallpaperFullScreenViewModel.setOriginalWallpaperDialog.value) {
        SetWallpaperDialog(
            dialogState = wallpaperFullScreenViewModel.setOriginalWallpaperDialog,
            context = context,
            wallpaperFullScreenViewModel,
            fullUrl
        )
    }

    LaunchedEffect(key1 = "bitmap", block = {
//        wallpaperFullScreenViewModel.fullUrl.value = fullUrl
        originalImage = getBitmapFromUrl(fullUrl)
        smallSizeImage = loadReducedSizeBitmapFromUrl(fullUrl)
    })

    var scale by remember { mutableStateOf(ContentScale.Crop) }
    var showFitScreenBtn by remember { mutableStateOf(true) }
    var showCropScreenBtn by remember { mutableStateOf(false) }
    val captureController = rememberCaptureController()

    BackHandler {
        navController.popBackStack()
        wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
        wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.BottomCenter
    ) {
        Capturable(controller = captureController, onCaptured = { bitmap, error ->
            if (bitmap != null) {
                saveMediaToStorage(bitmap.asAndroidBitmap(), context)
            }
        }, content = {
            SubcomposeAsyncImage(
                model = smallSizeImage,
                contentScale = scale,
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
                            contentScale = scale,
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

        if (wallpaperFullScreenViewModel.interstitalState.value) {
            MainInterstitialAd()
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (expanded) ContentAlpha.medium else ContentAlpha.disabled)
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
                        if ((wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f &&
                                    wallpaperFullScreenViewModel.saturationSliderValue.value != 1f)
                        ) {
                            FilledIconButton(
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colors.bottomAppBarContentColor,
                                    contentColor = Color.White
                                ),
                                onClick = { captureController.capture() }) {
                                Icon(
                                    imageVector = Icons.Rounded.Download,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    originalImage?.let {
                                        saveMediaToStorage(
                                            it,
                                            context
                                        )
                                        wallpaperFullScreenViewModel.interstitalState.value = true
                                    }
                                }) {
                                Icon(
                                    imageVector = Icons.Rounded.Download,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }

                        if (showFitScreenBtn) {
                            IconButton(
                                onClick = {
                                    scale = ContentScale.Fit
                                    showFitScreenBtn = false
                                    showCropScreenBtn = true
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Fullscreen,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }

                        if (showCropScreenBtn) {
                            IconButton(
                                onClick = {
                                    scale = ContentScale.Crop
                                    showCropScreenBtn = false
                                    showFitScreenBtn = true
                                }) {
                                Icon(
                                    imageVector = Icons.Rounded.Fullscreen,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.bottomAppBarContentColor
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                expanded = !expanded
                            }) {
                            Icon(
                                imageVector = if (expanded) Icons.Rounded.ArrowDropUp else Icons.Rounded.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.White
                            )
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
                                .padding(start = 8.dp, end = 0.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            androidx.compose.material3.Slider(
                                modifier = Modifier.weight(4.3f),
                                value = wallpaperFullScreenViewModel.saturationSliderPosition.value,
                                onValueChange = {
                                    wallpaperFullScreenViewModel.saturationSliderPosition.value = it
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
                            }, modifier = Modifier.weight(1f)) {
                                Icon(
                                    imageVector = Icons.Default.FormatColorReset,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

       AnimatedVisibility(visible = wallpaperFullScreenViewModel.saturationSliderPosition.value == 1f &&
               wallpaperFullScreenViewModel.saturationSliderValue.value == 1f,
           enter = slideInVertically { 3000 } + scaleIn(),
           exit = slideOutVertically { 5000 } + scaleOut()) {
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(20.dp),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.Center
           ) {

               FloatingActionButton(
                   onClick = {
                       shareWallpaper(context, originalImage)
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


               FloatingActionButton(
                   onClick = {
                       wallpaperFullScreenViewModel.id += 1
                       val favUrl = FavouriteUrls(wallpaperFullScreenViewModel.id, fullUrl, regularUrl)
                       favUrlsViewModel.addToFav(favUrl)
                       Toast.makeText(context, "Added to Favourites!", Toast.LENGTH_SHORT).show()
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

               FloatingActionButton(
                   onClick = {
                       wallpaperFullScreenViewModel.setOriginalWallpaperDialog.value = true
                       wallpaperFullScreenViewModel.interstitalState.value = true
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

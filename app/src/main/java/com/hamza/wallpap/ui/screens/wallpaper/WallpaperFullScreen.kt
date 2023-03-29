package com.hamza.wallpap.ui.screens.wallpaper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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
import com.hamza.wallpap.util.saveMediaToStorage
import com.hamza.wallpap.util.shareWallpaper
import java.net.URL

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

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)

    var image: Bitmap? = null
    val context = LocalContext.current

    val thread = Thread {
        try {
            val url = URL(fullUrl)
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    thread.start()

    var scale by remember { mutableStateOf(ContentScale.Crop) }
    var showFitScreenBtn by remember { mutableStateOf(true) }
    var showCropScreenBtn by remember { mutableStateOf(false) }

    BackHandler {
        navController.popBackStack()
    }

//
//        DisposableEffect(key1 = "ad", effect = {
//        var interstitialAd: InterstitialAd?
//        val adRequest = AdRequest.Builder().build()
//
//        InterstitialAd.load(
//            context,
//            "ca-app-pub-3940256099942544/1033173712",
//            adRequest,
//            object : InterstitialAdLoadCallback() {
//                override fun onAdFailedToLoad(p0: LoadAdError) {
//                    interstitialAd = null
//                }
//
//                override fun onAdLoaded(p0: InterstitialAd) {
//                    interstitialAd = p0
//                    interstitialAd?.show(context as Activity)
//                }
//            })
//        onDispose { interstitialAd = null }
//    })

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.BottomCenter
    ) {

        SubcomposeAsyncImage(
            model = fullUrl,
            contentScale = scale,
            contentDescription = null
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
                SubcomposeAsyncImageContent(modifier = Modifier.fillMaxSize())
            }
        }

        if (wallpaperFullScreenViewModel.interstitalState.value) {
            MainInterstitialAd()
        }


        if (wallpaperFullScreenViewModel.dialogState.value) {
            SetWallpaperDialog(
                dialogState = wallpaperFullScreenViewModel.dialogState,
                context = context,
                wallpaperFullScreenViewModel,
                fullUrl
            )
        }

        Surface(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.disabled)
                .align(Alignment.TopEnd),
            color = Color.Black
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Row {

                    IconButton(
                        onClick = {
                            image?.let {
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
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            FloatingActionButton(
                onClick = {
                    shareWallpaper(context, image)
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
                    wallpaperFullScreenViewModel.dialogState.value = true
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

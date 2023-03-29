package com.hamza.wallpap.ui.screens.favourite


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.screens.common.SetWallpaperDialog
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreenViewModel
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.util.saveMediaToStorage
import com.hamza.wallpap.util.shareWallpaper
import java.net.URL

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun FavouriteWallpaperFullScreen(
    fullUrl: String,
    regularUrl: String,
    navController: NavHostController,
    favUrlsViewModel: FavUrlsViewModel,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.BottomCenter
    ) {
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

        if (wallpaperFullScreenViewModel.dialogState.value) {
            SetWallpaperDialog(
                dialogState = wallpaperFullScreenViewModel.dialogState,
                context = context,
                wallpaperFullScreenViewModel,
                fullUrl
            )
        }

        var scale by remember { mutableStateOf(ContentScale.Crop) }
        var showFitScreenBtn by remember { mutableStateOf(true) }
        var showCropScreenBtn by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(
            model = fullUrl,
            contentScale = scale,
            contentDescription = null
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
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
            } else {
                SubcomposeAsyncImageContent(modifier = Modifier.fillMaxSize())
            }
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
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        },
                    tint = Color.White
                )

                Row {

                    Icon(
                        imageVector = Icons.Rounded.Download,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                image?.let {
                                    saveMediaToStorage(
                                        it,
                                        context
                                    )
                                }
                            },
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.padding(end = 10.dp))

                    if (showFitScreenBtn) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    scale = ContentScale.Fit
                                    showFitScreenBtn = false
                                    showCropScreenBtn = true
                                },
                            tint = Color.White
                        )
                    }

                    if (showCropScreenBtn) {
                        Icon(
                            imageVector = Icons.Rounded.Fullscreen,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    scale = ContentScale.Crop
                                    showCropScreenBtn = false
                                    showFitScreenBtn = true
                                },
                            tint = Color.White
                        )
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
                    favUrlsViewModel.deleteFavouriteUrl(
                        FavouriteUrls(
                            wallpaperFullScreenViewModel.id,
                            fullUrl,
                            regularUrl
                        )
                    )
                    Toast.makeText(context, "Removed!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(8.dp),
                backgroundColor = MaterialTheme.colors.bottomAppBarBackgroundColor
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.bottomAppBarContentColor
                )
            }

            FloatingActionButton(
                onClick = {
                    wallpaperFullScreenViewModel.dialogState.value = true
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

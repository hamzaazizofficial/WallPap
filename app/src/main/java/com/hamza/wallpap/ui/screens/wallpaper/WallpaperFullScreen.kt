package com.hamza.wallpap.ui.screens.wallpaper

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.screens.common.SetWallpaperDialog
import com.hamza.wallpap.ui.screens.common.admob.MainInterstitialAd
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.systemBarColor
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun WallpaperFullScreen(regularUrl: String, fullUrl: String, navController: NavHostController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val wallpaperFullScreenViewModel: WallpaperFullScreenViewModel = viewModel()
    var viewModel: FavUrlsViewModel = viewModel()
//    val context: Context = LocalContext.current
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
        var image: Bitmap? = null
        var data by remember { mutableStateOf(regularUrl) }
        val context = LocalContext.current

        val painter = rememberImagePainter(data = data) {
            crossfade(durationMillis = 1)
//            error(R.drawable.ic_placeholder)
//            placeholder(R.drawable.loading)

        }


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

        if (showFitScreenBtn) {
            LinearProgressIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colors.secondary
            )
        }

        Image(
            contentScale = scale,
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Unsplash Image"
        )


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

                    IconButton(
                        onClick = {
                            data = fullUrl
                            wallpaperFullScreenViewModel.turnHdWallpaperChecked.value = true
                        }) {
                        if (wallpaperFullScreenViewModel.turnHdWallpaperChecked.value) {
                            Icon(
                                imageVector = Icons.Default.HighQuality,
                                contentDescription = null,
                                tint = MaterialTheme.colors.bottomAppBarContentColor
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.HighQuality,
                                contentDescription = null,
                                tint = Color.White
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
                    val intent = Intent(Intent.ACTION_SEND).setType("image/*")

                    val path = MediaStore.Images.Media.insertImage(
                        context.contentResolver,
                        image,
                        "${System.currentTimeMillis()}",
                        null
                    )

                    if (path != null) {
                        val uri = Uri.parse(path)
                        intent.putExtra(Intent.EXTRA_STREAM, uri)
                        intent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Download WallPap for more exciting WallPapers!"
                        )
                        context.startActivity(intent)
                    }
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
                    val favUrl = FavouriteUrls(wallpaperFullScreenViewModel.id, fullUrl)
                    viewModel.addToFav(favUrl)
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


@RequiresApi(Build.VERSION_CODES.N)
fun setWallPaper(context: Context, fullUrl: String, wallpaperAs: Int) {

    val metrics = DisplayMetrics()
    val windowsManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
    windowsManager.defaultDisplay.getMetrics(metrics)

    val screenWidth = metrics.widthPixels
    val screenHeight = metrics.heightPixels.minus(300)

    val wallpaperManager = WallpaperManager.getInstance(context)
    wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)

    val width = wallpaperManager.desiredMinimumWidth
    val height = wallpaperManager.desiredMinimumHeight
    Toast.makeText(context, "Setting your Wallpaper...", Toast.LENGTH_LONG).show()

    val thread = Thread {
        try {
            val url = URL(fullUrl)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val wallpaper = Bitmap.createScaledBitmap(image, width, height, true)
            when (wallpaperAs) {
                1 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                2 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                3 -> {
                    wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                    wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                }
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    thread.start()
}


fun saveMediaToStorage(bitmap: Bitmap, context: Context) {
    //Generating a file name
    val filename = "${System.currentTimeMillis()}.jpg"

    //Output stream
    var fos: OutputStream? = null

    //For devices running android >= Q
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        //getting the contentResolver

        context?.contentResolver?.also { resolver ->

            //Content resolver will process the contentvalues
            val contentValues = ContentValues().apply {
                //putting file information in content values
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/WallPap")
            }

            //Inserting the contentValues to contentResolver and getting the Uri
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            //Opening an outputstream with the Uri that we got
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        //These for devices running on android < Q
        //So I don't think an explanation is needed here
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + File.separator + "WallPap"

        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }

    fos?.use {
        //Finally writing the bitmap to the output stream that we opened
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//        context?.toast("Saved to Photos")
        Toast.makeText(context, "Saved to Gallery!", Toast.LENGTH_SHORT).show()
    }
}


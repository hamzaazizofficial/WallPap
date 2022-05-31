package com.hamza.wallpap.data.screens.wallpaper

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.compose.rememberImagePainter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

@Composable
fun WallpaperFullScreen(regularUrl: String, fullUrl: String) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.BottomCenter
    ) {

        var data by remember { mutableStateOf(regularUrl) }

        val context = LocalContext.current

        val painter = rememberImagePainter(data = data) {
            crossfade(durationMillis = 1)
//            error(R.drawable.ic_placeholder)
//            placeholder(R.drawable.loading)
        }

//        var image: Bitmap? = null
//
//        LaunchedEffect(key1 = true) {
//
//            val thread = Thread {
//                try {
//                    //Your code goes here
//                    val url = URL(fullUrl)
//                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
////                    wallpaperManager.setBitmap(image)
//                } catch (e: java.lang.Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            thread.start()
//        }

        var scale by remember { mutableStateOf(ContentScale.Crop) }
        var showFitScreenBtn by remember { mutableStateOf(true) }
        var showCropScreenBtn by remember { mutableStateOf(false) }

        if (showFitScreenBtn) {
            LinearProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
        }

        Image(
            contentScale = scale,
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Unsplash Image",
        )

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

                Spacer(modifier = Modifier.padding(end = 10.dp))

                Icon(
                    imageVector = Icons.Default.HighQuality,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            data = fullUrl
                        },
                    tint = Color.White
                )
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
//                    fullUrlUri =
//                        homeViewModel.unsplashBitmap!!.saveImage(
//                            context
//                        )
                },
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(0.5f),
                backgroundColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null
                )
            }

            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(0.6f),
                backgroundColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }

            FloatingActionButton(
                onClick = {
                    setWallPaper(context, fullUrl)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(0.5f),
                backgroundColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.ImagesearchRoller,
                    contentDescription = null
                )
            }
        }
    }
}

fun setWallPaper(context: Context, fullUrl: String) {

    val wallpaperManager = WallpaperManager.getInstance(context)
    Toast.makeText(context, "Setting your Wallpaper...", Toast.LENGTH_LONG).show()

    val thread = Thread {
        try {
            //Your code goes here
            val url = URL(fullUrl)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            wallpaperManager.setBitmap(image)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    thread.start()

//    val wallpaperManager = WallpaperManager.getInstance(context)
//            Snackbar.make(context, "Wallpaper set successfully", Snackbar.LENGTH_LONG)
//                .show()
//    Toast.makeText(context, "Wallpaper Set Successfully!", Toast.LENGTH_SHORT).show()
//
//    try {
////        val url = URL(fullUrl)
////        val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
////        wallpaperManager.setBitmap(image)
//    } catch (e: IOException) {
//        println(e)
//    }

//
//    try {
//
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }

//    val uri = Uri.parse(fullUrl)
//
//    val wallpaperManager = WallpaperManager.getInstance(this@MainActivity)
//    val uri = Uri.parse("android.resource://lv.revo.inspicfootballhd/drawable/v1")
//    val intent = Intent(wallpaperManager.getCropAndSetWallpaperIntent(uri))
//    startActivity(intent)

//    Glide.with(context).asBitmap().load(fullUrl).placeholder(R.drawable.ic_placeholder).into(object : CustomTarget<Bitmap>() {
//        override fun onResourceReady(
//            resource: Bitmap,
//            transition: Transition<in Bitmap>?
//        ) {
//            val wallpaperManager = WallpaperManager.getInstance(context)
////            Snackbar.make(context, "Wallpaper set successfully", Snackbar.LENGTH_LONG)
////                .show()
//            Toast.makeText(context, "Wallpaper Set Successfully!", Toast.LENGTH_SHORT).show()
//            try {
//                wallpaperManager.setBitmap(resource)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//
//        override fun onLoadCleared(placeholder: Drawable?) {
//
//        }
//    })
}

fun Bitmap.saveImage(context: Context): Uri? {
    if (android.os.Build.VERSION.SDK_INT >= 29) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/COMPRESSR")
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "img_${SystemClock.uptimeMillis()}")

        val uri: Uri? =
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            saveImageToStream(this, context.contentResolver.openOutputStream(uri), 100)
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.contentResolver.update(uri, values, null, null)
            return uri
        }
    } else {
        val directory =
            File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + "COMPRESSR"
            )
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = "img_${SystemClock.uptimeMillis()}" + ".jpeg"
        val file = File(directory, fileName)
        saveImageToStream(this, FileOutputStream(file), 100)
        if (file.absolutePath != null) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            return Uri.fromFile(file)
        }
    }
    return null
}


fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?, compressQuality: Int) {
    if (outputStream != null) {
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

package com.hamza.wallpap.util

import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreenViewModel

@RequiresApi(Build.VERSION_CODES.N)
fun setWallPaper(
    context: Context,
    fullUrl: String,
    wallpaperAs: Int,
    finalImageBitmap: Bitmap?,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel
) {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val screenWidth = windowManager.defaultDisplay.width
    val screenHeight = windowManager.defaultDisplay.height
    val wallpaperManager = WallpaperManager.getInstance(context)
    wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)
    val width = wallpaperManager.desiredMinimumWidth
    val height = wallpaperManager.desiredMinimumHeight

//    Toast.makeText(context, "Setting your Wallpaper...", Toast.LENGTH_LONG).show()
//    wallpaperFullScreenViewModel.interstitialState.value = !wallpaperFullScreenViewModel.interstitialState.value

    val thread = Thread {
        try {
            when (wallpaperFullScreenViewModel.finalScaleState) {
                wallpaperFullScreenViewModel.scaleCropState -> {
                    val wallpaper = finalImageBitmap?.let {
                        scaleAndCropBitmap(it, screenWidth, screenHeight)
                    }
                    when (wallpaperAs) {
                        1 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                        2 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                        3 -> {
                            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                        }
                    }
                }
                wallpaperFullScreenViewModel.scaleStretchState -> {
                    val wallpaper = finalImageBitmap?.let { Bitmap.createScaledBitmap(it, width, height, true) }
                    when (wallpaperAs) {
                        1 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                        2 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                        3 -> {
                            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                        }
                    }
                }
                else -> {
                    val wallpaper = finalImageBitmap?.let {
                        scaleAndFitBitmap(it, screenWidth, screenHeight)
                    }
                    when (wallpaperAs) {
                        1 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                        2 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                        3 -> {
                            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    thread.start()

//    val metrics = DisplayMetrics()
//    val windowsManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//    windowsManager.defaultDisplay.getMetrics(metrics)
//
//    val screenWidth = metrics.widthPixels
//    val screenHeight = metrics.heightPixels.minus(300)
//
//    val wallpaperManager = WallpaperManager.getInstance(context)
//    wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)
//
//    val width = wallpaperManager.desiredMinimumWidth
//    val height = wallpaperManager.desiredMinimumHeight
//    Toast.makeText(context, "Setting your Wallpaper...", Toast.LENGTH_LONG).show()
//
//    val thread = Thread {
//        try {
//            val wallpaper =
//                finalImageBitmap?.let { Bitmap.createScaledBitmap(it, width, height, true) }
//            when (wallpaperAs) {
//                1 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
//                2 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
//                3 -> {
//                    wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
//                    wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
//                }
//            }
//
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    thread.start()
}

private fun scaleAndFitBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
    val sourceWidth = bitmap.width
    val sourceHeight = bitmap.height

    val widthRatio = targetWidth.toFloat() / sourceWidth.toFloat()
    val heightRatio = targetHeight.toFloat() / sourceHeight.toFloat()

    val scaleFactor = widthRatio.coerceAtMost(heightRatio)

    val scaledWidth = (sourceWidth * scaleFactor).toInt()
    val scaledHeight = (sourceHeight * scaleFactor).toInt()

    val offsetX = (targetWidth - scaledWidth) / 2
    val offsetY = (targetHeight - scaledHeight) / 2

    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)

    val outputBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(outputBitmap)
    canvas.drawBitmap(scaledBitmap, offsetX.toFloat(), offsetY.toFloat(), null)

    return outputBitmap
}

private fun scaleAndCropBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
    val sourceWidth = bitmap.width
    val sourceHeight = bitmap.height

    val widthRatio = targetWidth.toFloat() / sourceWidth.toFloat()
    val heightRatio = targetHeight.toFloat() / sourceHeight.toFloat()

    val scaleFactor = widthRatio.coerceAtLeast(heightRatio)

    val scaledWidth = (sourceWidth * scaleFactor).toInt()
    val scaledHeight = (sourceHeight * scaleFactor).toInt()

    val offsetX = (targetWidth - scaledWidth) / 2
    val offsetY = (targetHeight - scaledHeight) / 2

    val scaledBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(scaledBitmap)
    val scaleMatrix = RectF(offsetX.toFloat(), offsetY.toFloat(), (offsetX + scaledWidth).toFloat(), (offsetY + scaledHeight).toFloat())
    canvas.drawBitmap(bitmap, null, scaleMatrix, null)

    return scaledBitmap
}

private fun centerCropBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
    val sourceWidth = bitmap.width
    val sourceHeight = bitmap.height

    val scale = if (sourceWidth > sourceHeight) {
        targetWidth.toFloat() / sourceWidth
    } else {
        targetHeight.toFloat() / sourceHeight
    }

    val scaledWidth = (scale * sourceWidth).toInt()
    val scaledHeight = (scale * sourceHeight).toInt()

    val startX = (targetWidth - scaledWidth) / 2
    val startY = (targetHeight - scaledHeight) / 2

    return Bitmap.createScaledBitmap(
        bitmap,
        scaledWidth,
        scaledHeight,
        true
    ).let {
        Bitmap.createBitmap(it, startX, startY, targetWidth, targetHeight)
    }
}